package com.sale.service.impl;

import com.sale.common.mail.EmailSender;
import com.sale.common.util.TokenGenerate;
import com.sale.dto.request.UserRequest;
import com.sale.enums.Status;
import com.sale.exceptions.userexceptions.UserApiException;
import com.sale.exceptions.userexceptions.UserBadRequestException;
import com.sale.exceptions.userexceptions.UserNotFoundException;
import com.sale.model.UserEntity;
import com.sale.repository.UserRepository;
import com.sale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.sale.common.constants.ExceptionMessageConstants.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity create(UserRequest request) throws UserApiException {
        request.validatePassword(request.getPassword());
        UserEntity userEntity = request.toUserEntity();
        String verifyCode = TokenGenerate.generateVerifyCode();
        userEntity.setVerifyCode(verifyCode);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setStatus(Status.INACTIVE);
        userEntity.setCarStatements(new HashSet<>());
        UserEntity result;
        try {
            result = userRepository.save(userEntity);
        } catch (Exception e) {
            throw new UserApiException(SAVING_USER_PROBLEM);
        }
        emailSender.send(
                request.getEmail(),
                "Verification code",
                "Your verification code is " + verifyCode
        );
        return result;
    }

    @Override
    public boolean verifyUser(String email, String verifyCode) throws UserApiException {
        UserEntity userEntity;
        try {
            userEntity = userRepository.getByEmail(email);
        } catch (Exception e) {
            throw new UserApiException(VERIFICATION_PROBLEM);
        }
        if (userEntity == null) {
            throw new UserNotFoundException(FOUND_USER_PROBLEM);
        }
        if (!userEntity.getVerifyCode().equals(verifyCode)) {
            throw new UserBadRequestException(INCORRECT_VERIFICATION);
        }
        userEntity.setStatus(Status.ACTIVE);
        userEntity.setVerifyCode(null);
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new UserApiException(VERIFICATION_PROBLEM);
        }
        return true;
    }

    @Override
    public UserEntity get(Integer id) throws UserApiException {
        Optional<UserEntity> userEntity;
        try {
            userEntity = userRepository.findById(id);
        } catch (Exception ex) {
            throw new UserApiException(GETTING_USER_PROBLEM);
        }
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException(FOUND_USER_PROBLEM);
        }
        return userEntity.get();
    }

    @Override
    public List<UserEntity> getAll() throws UserApiException {
        List<UserEntity> userEntities;
        try {
            userEntities = userRepository.findAll();
        } catch (Exception ex) {
            throw new UserApiException(GETTING_USER_PROBLEM);
        }
        return userEntities;
    }

    @Override
    public UserEntity update(Integer id, UserRequest userRequest) throws UserApiException {
        Optional<UserEntity> result;
        userRequest.validatePassword(userRequest.getPassword());
        try {
            result = userRepository.findById(id);
        } catch (Exception ex) {
            throw new UserApiException(UPDATING_USER_PROBLEM);
        }
        if (result.isEmpty()) {
            throw new UserNotFoundException(FOUND_USER_PROBLEM);
        }
        UserEntity userEntity = result.get();
        userEntity.setName(userRequest.getName());
        userEntity.setSurname(userRequest.getSurname());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setRole(userRequest.getRole());

        UserEntity newUser;
        try {
            newUser = userRepository.save(userEntity);
        } catch (Exception ex) {
            throw new UserApiException(UPDATING_USER_PROBLEM);
        }
        return newUser;
    }

    @Override
    public void delete(Integer id) throws UserApiException {
        Optional<UserEntity> userEntity;
        try {
            userEntity = userRepository.findById(id);
        } catch (Exception ex) {
            throw new UserApiException(DELETING_USER_PROBLEM);
        }
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException(FOUND_USER_PROBLEM);
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new UserApiException(DELETING_USER_PROBLEM);
        }
    }
}
