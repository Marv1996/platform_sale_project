package com.sale.exceptions.userexceptions;

import com.sale.common.exceptions.PlatformBadRequestException;

public class UserBadRequestException extends PlatformBadRequestException {
    public UserBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
