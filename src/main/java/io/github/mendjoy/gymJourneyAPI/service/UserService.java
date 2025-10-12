package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.UserRequestDto;
import io.github.mendjoy.gymJourneyAPI.dto.UserResponseDto;
import io.github.mendjoy.gymJourneyAPI.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

   public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail())){
            throw GymJourneyException.alreadyExists("E-mail já cadastrado!");
        }
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = modelMapper.map(userRequestDto, User.class);
        user.setPassword(encodedPassword);
        User newUser = userRepository.save(user);
        return modelMapper.map(newUser, UserResponseDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow( () -> GymJourneyException.notFound("Usuário nao foi encontrado"));
    }
}
