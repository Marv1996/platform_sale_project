package com.sale.exceptions.userexceptions;

import com.sale.common.exceptions.PlatformApiException;

public class UserApiException extends PlatformApiException {
    public UserApiException(String errorMessage) {
        super(errorMessage);
    }
}
