package com.sale.config;

import com.sale.exceptions.userexceptions.UserApiException;
import com.sale.exceptions.userexceptions.UserNotFoundException;
import com.sale.model.UserEntity;
import com.sale.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sale.common.constants.ExceptionMessageConstants.AUTHORIZATION_PROBLEM;
import static com.sale.common.constants.ExceptionMessageConstants.USERNAME_PASSWORD_PROBLEM;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity;
        try {
            userEntity = userRepository.getByEmail(username);
        } catch (Exception ex) {
            throw new UserApiException(AUTHORIZATION_PROBLEM);
        }
        if (userEntity == null) {
            throw new UserNotFoundException(USERNAME_PASSWORD_PROBLEM);
        }
        List<String> roles = new ArrayList<>();
        roles.add(userEntity.getRole().toString());

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}
