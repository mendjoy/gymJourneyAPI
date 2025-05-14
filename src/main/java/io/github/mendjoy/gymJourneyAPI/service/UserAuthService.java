package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.dto.user.UserAuthDTO;
import io.github.mendjoy.gymJourneyAPI.dto.user.UserRegisterDTO;
import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import io.github.mendjoy.gymJourneyAPI.entity.user.UserRole;
import io.github.mendjoy.gymJourneyAPI.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserAuthService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public UserAuthDTO authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Email não cadastrado!");
        }

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Senha incorreta!");
        }

        String token = tokenService.generateToken(user);

        return new UserAuthDTO(user.getId(), user.getName(), user.getUsername(),user.getRole(), token);

    }

    public UserAuthDTO register(UserRegisterDTO userRegisterDTO){
        if(userRepository.existsByEmail(userRegisterDTO.getEmail())){
            throw new BadCredentialsException("E-mail já cadastrado.");
        }

        User newUser = new User(userRegisterDTO.getEmail(),
                                userRegisterDTO.getName(),
                                userRegisterDTO.getPhone(),
                                userRegisterDTO.getBirthDate(),
                                passwordEncoder.encode(userRegisterDTO.getPassword()),
                                UserRole.USER);

        userRepository.save(newUser);

        return authenticate(userRegisterDTO.getEmail(), userRegisterDTO.getPassword());
    }

    public User getUserAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        return (User) authentication.getPrincipal();
    }

}
