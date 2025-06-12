package io.github.mendjoy.gymJourneyAPI.service.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.mendjoy.gymJourneyAPI.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    public String generateToken(User user) {

        try {

            Algorithm algorithm = getAlgorithm();
            return JWT.create()
                      .withIssuer("gymJourneyAPI")
                      .withSubject(user.getUsername())
                      .withClaim("id", user.getId())
                      .withExpiresAt(generateExpirationDate())
                      .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar Token");
        }
    }

    public String validateToken(String token) {

        try {

            Algorithm algorithm = getAlgorithm();
            return JWT.require(algorithm)
                      .withIssuer("gymJourneyAPI")
                      .build()
                      .verify(token)
                      .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusDays(3).toInstant(ZoneOffset.of("-03:00"));
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
