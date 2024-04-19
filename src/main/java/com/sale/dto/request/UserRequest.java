package com.sale.dto.request;

import com.sale.enums.Role;
import com.sale.exceptions.userexceptions.UserBadRequestException;
import com.sale.model.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.sale.common.constants.ExceptionMessageConstants.PASSWORD_PROBLEM;
import static com.sale.common.constants.ExceptionMessageConstants.SHORT_PASSWORD;

@Getter
@Setter
@Builder
public class UserRequest {
    private static final String NAME_REGEX = "[A-Za-z]+";
    private static final String NAME_NULL_MSG = "User name can't be null or empty";

    private static final String SURNAME_REGEX = "[A-Za-z //-]+";
    private static final String SURNAME_NULL_MSG = "User surname can't be null or empty";

    private static final String EMAIL_REGEX =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String EMAIL_FORMAT_MSG = "Email doesn't meet the requirements";

    @NotEmpty(message = NAME_NULL_MSG)
    @Pattern(regexp = NAME_REGEX, message = NAME_NULL_MSG)
    @Schema(description = "The name of user", example = "Koryun")
    private String name;
    @NotEmpty(message = SURNAME_NULL_MSG)
    @Pattern(regexp = SURNAME_REGEX, message = SURNAME_NULL_MSG)
    @Schema(description = "The surname of user", example = "Yenokyan")
    private String surname;
    @NotEmpty(message = EMAIL_FORMAT_MSG)
    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_FORMAT_MSG)
    @Schema(description = "The email of user", example = "koryun.yenokyan@gmail.com")
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity toUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(0);
        userEntity.setName(name);
        userEntity.setSurname(surname);
        userEntity.setEmail(email);
        userEntity.setRole(role);
        return userEntity;
    }

    public void validatePassword(String password) {
        if (password.length() < 8) {
            throw new UserBadRequestException(SHORT_PASSWORD);
        }

        int countOfDigit = 0;
        int countOfUppercase = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                countOfDigit++;
            } else if (Character.isUpperCase(c)) {
                countOfUppercase++;
            }
        }
        if (countOfDigit < 1 || countOfUppercase < 1) {
            throw new UserBadRequestException(PASSWORD_PROBLEM);
        }
    }
}
