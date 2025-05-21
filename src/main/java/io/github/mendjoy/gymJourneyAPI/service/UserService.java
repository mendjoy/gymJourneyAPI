package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.dto.user.UserUpdateDTO;
import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.user.UserRole;
import io.github.mendjoy.gymJourneyAPI.exception.custom.CustomGymJourneyApiException;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthService userAuthService;

    public UserService(UserRepository userRepository, UserAuthService userAuthService) {
        this.userRepository = userRepository;
        this.userAuthService = userAuthService;
    }

    public void grantAdminRole(Integer id){
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        user.setRole(UserRole.ADMIN);
        userRepository.save(user);
    }

    public void updateUser(Integer id, UserUpdateDTO userUpdateDTO){
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        User userAuth = userAuthService.getUserAuthenticate();

        if (!user.equals(userAuth)) {
            throw new CustomGymJourneyApiException(HttpStatus.FORBIDDEN, "Você não tem permissão para realizar esta ação.");
        }

        if(userUpdateDTO.getName() != null){
            user.setName(userUpdateDTO.getName());
        }

        if (userUpdateDTO.getPhone() != null){
            user.setPhone(userUpdateDTO.getPhone());
        }

        userRepository.save(user);
    }
}
