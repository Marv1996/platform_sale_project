package com.sale.controller;

import com.sale.dto.request.LoginRequest;
import com.sale.exceptions.userexceptions.UserApiException;
import com.sale.service.TokenService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.sale.common.constants.SecurityConstants.SECURITY_URL;

@RestController
@RequestMapping(SECURITY_URL)
@Hidden
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public @ResponseBody String login(@RequestBody LoginRequest loginRequest) throws UserApiException {
        return tokenService.createToken(loginRequest);
    }
}
