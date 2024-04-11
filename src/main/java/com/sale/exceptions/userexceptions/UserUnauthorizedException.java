package com.sale.exceptions.userexceptions;

import com.sale.common.exceptions.PlatformUnauthorizedException;

public class UserUnauthorizedException extends PlatformUnauthorizedException {
    public UserUnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
