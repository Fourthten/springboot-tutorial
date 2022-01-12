package fxs.fourthten.springtutorial.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fxs.fourthten.springtutorial.config.utility.ConstantUtil;
import fxs.fourthten.springtutorial.domain.model.User;
import fxs.fourthten.springtutorial.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthorizationUtil {
    @Autowired
    UserRepo userRepo;

    public String getToken(HttpServletRequest request) {
        if (request == null) throw new ValidationException("Authorization not found");
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            return token;
        } else {
            throw new ValidationException("Token not found");
        }
    }

    public User getUserFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(ConstantUtil.API_SECRET_KEY.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();
            User user = userRepo.findOneByUsername(username);
            return user;
        } catch (Exception exception) {
            throw new ValidationException("Error get user from token: " + exception.getMessage());
        }
    }
}
