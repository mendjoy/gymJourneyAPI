package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.Role;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.domain.enums.RoleName;
import io.github.mendjoy.gymJourneyAPI.dto.role.RoleDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserPasswordDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterRequestDto;
import io.github.mendjoy.gymJourneyAPI.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.repository.RoleRepository;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepository roleRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findByEmailIgnoreCaseAndVerifiedTrueAndActiveTrue(username).orElseThrow( () -> new UsernameNotFoundException("Usuário nao foi encontrado"));
    }

   public UserDto registerUser(UserRegisterRequestDto userRegisterRequestDto) {

        if(userRepository.existsByEmail(userRegisterRequestDto.getEmail())){
            throw GymJourneyException.alreadyExists("E-mail já cadastrado!");
        }

        Role role = roleRepository.findByName(RoleName.USER).orElseThrow(() -> GymJourneyException.notFound("Papel do usuário nao encontrado!"));
        String encodedPassword = passwordEncoder.encode(userRegisterRequestDto.getPassword());
        User user =  modelMapper.map(userRegisterRequestDto, User.class);
        user.setPassword(encodedPassword);
        user.setVerified(false);
        user.setToken(generateVerificationToken());
        user.setExpirationToken(LocalDateTime.now().plusMinutes(30));
        user.setActive(true);
        user.setRoles(List.of(role));

        User newUser = userRepository.save(user);
       // emailService.sendVerificationEmail(user);
        return modelMapper.map(newUser, UserDto.class);
    }

    public void verifyEmail(String token) {
        User user = userRepository.findByToken(token).orElseThrow( () -> GymJourneyException.notFound("Usuário nao foi encontrado"));
        if(user.getExpirationToken().isBefore(LocalDateTime.now())){
            throw GymJourneyException.badRequest("Token expirado, solicite um novo email de verificação.");
        }
        user.setVerified(true);
        user.setToken(null);
        user.setExpirationToken(null);
        userRepository.save(user);
    }

    public UserDto getAuthenticatedUser(User authenticatedUser) {
        User user = userRepository.findById(authenticatedUser.getId()).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        return modelMapper.map(user, UserDto.class);
    }

    private String generateVerificationToken(){
        return UUID.randomUUID().toString();
    }

    public void disableUser(Long userId, User authenticatedUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));

        boolean isAdmin = authenticatedUser.getRoles().stream().anyMatch(role -> role.getName() == RoleName.ADMIN);
        boolean isSameUser = user.getId().equals(authenticatedUser.getId());

        if (!isAdmin && !isSameUser) {
            throw GymJourneyException.forbidden("Você não tem permissão para inativar este usuário.");
        }

        user.setActive(false);
        userRepository.save(user);
    }

    public void addRole(Long id, RoleDto roleDto) {
        User user = userRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        Role role = roleRepository.findByName(RoleName.valueOf(roleDto.getRoleName())).orElseThrow(() -> GymJourneyException.notFound("Papel de usuario não encontrado"));

        if(user.getRoles().contains(role)){
            throw GymJourneyException.conflit("Usuário já possui papel de " + roleDto.getRoleName());
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void removeRole(Long id, RoleDto roleDto) {
        User user = userRepository.findById(id).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        Role role = roleRepository.findByName(RoleName.valueOf(roleDto.getRoleName())).orElseThrow(() -> GymJourneyException.notFound("Papel de usuario não encontrado"));

        if (!user.getRoles().contains(role)) {
            throw GymJourneyException.badRequest("O usuário não possui esse papel.");
        }

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public void changePassword(UserPasswordDto userPasswordDto, User authenticatedUser) {
        User user = userRepository.findById(authenticatedUser.getId()).orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));

        if(!passwordEncoder.matches(userPasswordDto.getCurrentPassword(), user.getPassword())){
            throw GymJourneyException.badRequest("Senha atual incorreta");
        }

        if(!userPasswordDto.getNewPassword().equals(userPasswordDto.getConfirmPassword())){
            throw GymJourneyException.badRequest("A nova senha e a confirmação não coincidem");
        }

        if (passwordEncoder.matches(userPasswordDto.getNewPassword(), user.getPassword())) {
            throw GymJourneyException.badRequest("A nova senha não pode ser igual à senha atual");
        }

        String encodedPassword = passwordEncoder.encode(userPasswordDto.getNewPassword());
        userRepository.save(user);
    }

}
