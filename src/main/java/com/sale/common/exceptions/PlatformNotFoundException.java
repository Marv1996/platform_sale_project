package com.sale.common.exceptions;

public abstract class PlatformNotFoundException extends RuntimeException {
    public PlatformNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
