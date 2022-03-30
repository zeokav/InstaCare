package edu.gatech.userservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthUtils {

    public String generateToken(String role, String email) {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        return JWT.create()
                .withHeader(header)
                .withClaim("role", role)
                .withClaim("email", email)
                .sign(Algorithm.HMAC256("shh-secret"));
    }

    public DecodedJWT parseToken(String token) {
        return JWT.decode(token);
    }

}
