package com.spring.player.jwt;



import com.auth0.jwt.JWT;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import com.spring.player.config.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TokenService {

    private AuthenticationManager authenticationManager;

    @Autowired
    public TokenService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    /* Trigger when we issue POST request to /login
    We also need to pass in {"username":"dan", "password":"dan123"} in the request body
     */

    public Authentication attemptAuthentication(JWTLogin jwtLogin) throws AuthenticationException {



        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                jwtLogin.getUsername(),
                jwtLogin.getPassword(),
                new ArrayList<>());

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }


    public  ResponseLogin successfulAuthentication(JWTLogin jwtLogin) throws Exception {
        ResponseLogin responseLogin = new ResponseLogin();
        Authentication authResult = attemptAuthentication(jwtLogin);
        // Grab principal
        UserPrinciple principal = (UserPrinciple) authResult.getPrincipal();
        responseLogin.setUsername(jwtLogin.getUsername());
        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +JWTProperties.EXPIRATION_TIME))
                .sign(HMAC512(JWTProperties.SECRET.getBytes()));
        responseLogin.setToken(token);

        return responseLogin;
    }
}
