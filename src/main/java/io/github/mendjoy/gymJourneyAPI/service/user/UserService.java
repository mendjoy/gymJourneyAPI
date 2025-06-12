package io.github.mendjoy.gymJourneyAPI.service.user;

import io.github.mendjoy.gymJourneyAPI.dto.user.UserPasswordUpdateDTO;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserUpdateDTO;
import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.user.UserRole;
import io.github.mendjoy.gymJourneyAPI.exception.custom.CustomGymJourneyApiException;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthService userAuthService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserAuthService userAuthService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userAuthService = userAuthService;
        this.passwordEncoder = passwordEncoder;
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
            throw new AccessDeniedException("Você não tem permissão para realizar esta ação.");
        }

        if(userUpdateDTO.getName() != null){
            user.setName(userUpdateDTO.getName());
        }

        if (userUpdateDTO.getPhone() != null){
            user.setPhone(userUpdateDTO.getPhone());
        }

        userRepository.save(user);
    }

    public void updatePassword(Integer id, UserPasswordUpdateDTO userPasswordUpdateDTO){
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        User userAuth = userAuthService.getUserAuthenticate();

        if (!user.equals(userAuth)) {
            throw new CustomGymJourneyApiException(HttpStatus.FORBIDDEN, "Você não tem permissão para realizar esta ação.");
        }

        if(!passwordEncoder.matches(userPasswordUpdateDTO.getCurrentPassword(), user.getPassword())){
            throw new BadCredentialsException("Senha atual incorreta!");
        }

        if (!userPasswordUpdateDTO.getNewPassword().equals(userPasswordUpdateDTO.getConfirmNewPassword())) {
            throw new BadCredentialsException("A nova senha e a confirmação não coincidem.");
        }

        user.setPassword(passwordEncoder.encode(userPasswordUpdateDTO.getNewPassword()));
        userRepository.save(user);
    }
}
