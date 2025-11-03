package io.github.mendjoy.gymJourneyAPI.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private Integer expiration;

    @Value("${api.security.token.issuer}")
    private String issuer;

    public String generateToken(User user){

        try {

            return JWT.create()
                      .withIssuer(issuer)
                      .withSubject(user.getUsername())
                      .withExpiresAt(expirationTime(expiration))
                      .sign(getAlgorithm());
        } catch (JWTCreationException exception){

            throw GymJourneyException.internalError("Erro ao gerar token de acesso!");

        }
    }

    public String verifyToken(String token){

        try {

            JWTVerifier verifier = JWT.require(getAlgorithm())
                                      .withIssuer(issuer)
                                      .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception){

            throw  GymJourneyException.forbidden("Falha na autenticação. Verifique suas credenciais.");

        }
    }

    private Instant expirationTime(Integer minutes){
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }

    private Algorithm getAlgorithm(){
        return  Algorithm.HMAC256(secret);
    }
}
