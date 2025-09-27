package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.dto.UserRequestDto;
import io.github.mendjoy.gymJourneyAPI.dto.UserResponseDto;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

   public UserResponseDto register(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail())){
            //throw new BadCredentialsException("E-mail já cadastrado.");
        }

        User newUser = userRepository.save(modelMapper.map(userRequestDto, User.class));
       return modelMapper.map(newUser, UserResponseDto.class);
    }
}
