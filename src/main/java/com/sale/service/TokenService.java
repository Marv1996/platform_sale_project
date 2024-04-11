package com.sale.service;

import com.sale.dto.request.LoginRequest;
import com.sale.exceptions.userexceptions.UserApiException;

public interface TokenService {
    String createToken(LoginRequest loginRequest) throws UserApiException;
}
