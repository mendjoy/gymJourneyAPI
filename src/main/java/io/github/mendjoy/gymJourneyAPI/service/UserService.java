package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterRequestDto;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserDto;
import io.github.mendjoy.gymJourneyAPI.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

   public UserDto registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if(userRepository.existsByEmail(userRegisterRequestDto.getEmail())){
            throw GymJourneyException.alreadyExists("E-mail já cadastrado!");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterRequestDto.getPassword());
        userRegisterRequestDto.setPassword(encodedPassword);
        User user =  modelMapper.map(userRegisterRequestDto, User.class);
        user.setVerified(false);
        user.setToken(generateVerificationToken());
        user.setExpirationToken(LocalDateTime.now().plusMinutes(30));

        User newUser = userRepository.save(user);
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

    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findByEmailIgnoreCaseAndVerifiedTrue(username).orElseThrow( () -> new UsernameNotFoundException("Usuário nao foi encontrado"));
    }

    private String generateVerificationToken(){
        return UUID.randomUUID().toString();
    }

}
