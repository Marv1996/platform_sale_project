package com.sale.service;

import com.sale.dto.request.UserRequest;
import com.sale.exceptions.userexceptions.UserApiException;
import com.sale.model.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity create(UserRequest request) throws UserApiException;

    boolean verifyUser(String email, String verifyCode) throws UserApiException;

    UserEntity get(Integer id) throws UserApiException;

    List<UserEntity> getAll() throws UserApiException;

    UserEntity update(Integer id, UserRequest userRequest) throws UserApiException;

    void delete(Integer id) throws UserApiException;
}
