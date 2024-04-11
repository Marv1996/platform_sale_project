package com.sale;

import com.sale.common.mail.EmailSender;
import com.sale.controller.UserController;
import com.sale.dto.request.UserRequest;
import com.sale.enums.Role;
import com.sale.exceptions.userexceptions.UserApiException;
import com.sale.model.UserEntity;
import com.sale.repository.UserRepository;
import com.sale.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailSender emailSender;
    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private UserController controller;

    UserRequest userRequest = UserRequest.builder()
            .name("TestName")
            .surname("TestSurname")
            .email("test@test.com")
            .password("Password123")
            .role(Role.USER)
            .build();

    UserEntity userEntity = UserEntity.builder()
            .id(1)
            .name("TestName")
            .surname("TestSurname")
            .email("test@test.com")
            .password("Password123")
            .verifyCode("testVC")
            .role(Role.USER)
            .build();

    @Test
    void testCreate() throws UserApiException {
        Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("userEntity");
        Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        UserEntity responseUserEntity = userService.create(userRequest);

        Assertions.assertEquals(responseUserEntity.getName(), userEntity.getName());
        Assertions.assertEquals(responseUserEntity.getSurname(), userEntity.getSurname());
        Assertions.assertEquals(responseUserEntity.getEmail(), userEntity.getEmail());
        Assertions.assertEquals(responseUserEntity.getPassword(), userEntity.getPassword());
    }

    @Test
    void testVerify() throws UserApiException {
        Mockito.when(userRepository.getByEmail(any(String.class))).thenReturn(userEntity);
        Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        boolean verifyUser = userService.verifyUser(userEntity.getEmail(), userEntity.getVerifyCode());

        Assertions.assertTrue(verifyUser);
    }

    @Test
    void testGet() throws UserApiException {
        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(userEntity));
        UserEntity responseUserEntity = userService.get(userEntity.getId());

        Assertions.assertEquals(responseUserEntity.getId(), userEntity.getId());
    }

    @Test
    void testGetAll() throws UserApiException {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>(List.of(userEntity)));
        List<UserEntity> responseUserEntities = userService.getAll();

        Assertions.assertEquals(responseUserEntities.get(0), userEntity);
    }

    @Test
    void testUpdate() throws UserApiException, NoSuchMethodException {
        Method updateUser = controller.getClass().getMethod("updateUser", Integer.class, UserRequest.class);
        PutMapping annotation = updateUser.getAnnotation(PutMapping.class);
        Assertions.assertNotNull(annotation);

        Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("userEntity");
        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(userEntity));
        Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        UserEntity responseUserEntity = userService.update(userEntity.getId(), userRequest);

        Assertions.assertEquals(responseUserEntity.getName(), userEntity.getName());
        Assertions.assertEquals(responseUserEntity.getSurname(), userEntity.getSurname());
        Assertions.assertEquals(responseUserEntity.getEmail(), userEntity.getEmail());
        Assertions.assertEquals(responseUserEntity.getPassword(), userEntity.getPassword());
    }

    @Test
    void testDelete() throws UserApiException {
        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(userEntity));
        userService.delete(userEntity.getId());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userEntity.getId());
    }
}
