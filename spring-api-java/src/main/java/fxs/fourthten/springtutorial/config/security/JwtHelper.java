package fxs.fourthten.springtutorial.config.security;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.interfaces.*;
import java.time.Instant;
import java.util.*;

@Slf4j @Component
public class JwtHelper {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtHelper(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String createJwtForClaims(String subject, Map<String, String> claims) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(Calendar.DATE, 1);

        // Create jwt
        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);
        claims.forEach(jwtBuilder::withClaim);

        return jwtBuilder
                .withNotBefore(new Date())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.RSA256(publicKey, privateKey));
    }
}
