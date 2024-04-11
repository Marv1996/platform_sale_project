package com.sale.exceptions.userexceptions;

import com.sale.common.exceptions.PlatformNotFoundException;

public class UserNotFoundException extends PlatformNotFoundException {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
