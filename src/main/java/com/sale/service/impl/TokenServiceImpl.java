package com.sale.service.impl;

import com.sale.config.JwtUtil;
import com.sale.dto.request.LoginRequest;
import com.sale.exceptions.userexceptions.UserApiException;
import com.sale.exceptions.userexceptions.UserBadRequestException;
import com.sale.model.UserEntity;
import com.sale.repository.UserRepository;
import com.sale.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.sale.common.constants.ExceptionMessageConstants.GETTING_TOKEN_PROBLEM;
import static com.sale.common.constants.ExceptionMessageConstants.INVALID_USERNAME_PASSWORD;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createToken(LoginRequest loginRequest) throws UserApiException {
        String token;
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String email = authenticate.getName();
            UserEntity userEntity = userRepository.getByEmail(email);
            userEntity.setPassword("");
            token = jwtUtil.createToken(userEntity);
        } catch (BadCredentialsException ex) {
            throw new UserBadRequestException(INVALID_USERNAME_PASSWORD);
        } catch (Exception ex) {
            throw new UserApiException(GETTING_TOKEN_PROBLEM);
        }
        return token;
    }
}
