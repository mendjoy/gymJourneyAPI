package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.config.mapper.UserMapper;
import io.github.mendjoy.gymJourneyAPI.domain.Role;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.domain.enums.RoleName;
import io.github.mendjoy.gymJourneyAPI.dto.role.RoleDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserPasswordDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterDto;
import io.github.mendjoy.gymJourneyAPI.repository.RoleRepository;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserMapper userMapper, RoleRepository roleRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmailIgnoreCaseAndVerifiedTrueAndActiveTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não foi encontrado"));
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public UserDto register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmail(userRegisterDto.email())) {
            throw GymJourneyException.alreadyExists("E-mail já cadastrado!");
        }

        Role role = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> GymJourneyException.notFound("Papel do usuário não encontrado!"));

        String encodedPassword = passwordEncoder.encode(userRegisterDto.password());
        User user = userMapper.toEntity(userRegisterDto);
        user.setPassword(encodedPassword);
        user.setVerified(false);
        user.setToken(generateVerificationToken());
        user.setExpirationToken(LocalDateTime.now().plusMinutes(30));
        user.setActive(true);
        user.setRoles(List.of(role));

        User newUser = userRepository.save(user);

        try {
            emailService.sendVerificationEmail(newUser);
        } catch (Exception e) {

        }

        return userMapper.toDto(newUser);
    }

    @Transactional
    public UserDto update(UserDto userDto, User authenticatedUser) {
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        user.update(userDto);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void addRole(Long id, RoleDto roleDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        Role role = roleRepository.findByName(RoleName.valueOf(roleDto.roleName()))
                .orElseThrow(() -> GymJourneyException.notFound("Papel de usuário não encontrado"));

        if (user.getRoles().contains(role)) {
            throw GymJourneyException.conflict("Usuário já possui papel de " + roleDto.roleName());
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public void removeRole(Long id, RoleDto roleDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        Role role = roleRepository.findByName(RoleName.valueOf(roleDto.roleName()))
                .orElseThrow(() -> GymJourneyException.notFound("Papel de usuário não encontrado"));

        if (!user.getRoles().contains(role)) {
            throw GymJourneyException.badRequest("O usuário não possui esse papel.");
        }

        if (user.getRoles().size() == 1) {
            throw GymJourneyException.badRequest("Não é possível remover o último papel do usuário.");
        }

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(UserPasswordDto userPasswordDto, User authenticatedUser) {
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));

        if (!passwordEncoder.matches(userPasswordDto.currentPassword(), user.getPassword())) {
            throw GymJourneyException.badRequest("Senha atual incorreta");
        }

        if (!userPasswordDto.newPassword().equals(userPasswordDto.confirmPassword())) {
            throw GymJourneyException.badRequest("A nova senha e a confirmação não coincidem");
        }

        if (passwordEncoder.matches(userPasswordDto.newPassword(), user.getPassword())) {
            throw GymJourneyException.badRequest("A nova senha não pode ser igual à senha atual");
        }

        String encodedPassword = passwordEncoder.encode(userPasswordDto.newPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public void disable(Long userId, User authenticatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));

        boolean isAdmin = authenticatedUser.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleName.ADMIN);
        boolean isSameUser = user.getId().equals(authenticatedUser.getId());

        if (!isAdmin && !isSameUser) {
            throw GymJourneyException.forbidden("Você não tem permissão para inativar este usuário.");
        }

        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public void enable(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));

        user.setActive(true);
        user.setVerified(false);
        user.setToken(generateVerificationToken());
        user.setExpirationToken(LocalDateTime.now().plusMinutes(30));

        User updatedUser = userRepository.save(user);


        try {
            emailService.sendVerificationEmail(updatedUser);
        } catch (Exception e) {

        }
    }

    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> GymJourneyException.notFound("Token inválido ou expirado"));

        if (user.getExpirationToken().isBefore(LocalDateTime.now())) {
            throw GymJourneyException.badRequest("Token expirado, solicite um novo email de verificação.");
        }

        user.setVerified(true);
        user.setToken(null);
        user.setExpirationToken(null);
        userRepository.save(user);
    }

    public UserDto getAuthenticatedUser(User authenticatedUser) {
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        return userMapper.toDto(user);
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> GymJourneyException.notFound("Usuário não encontrado"));
        return userMapper.toDto(user);
    }
}