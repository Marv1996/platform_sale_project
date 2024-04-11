package com.sale.common.exceptions;

public abstract class PlatformUnauthorizedException extends RuntimeException {
    public PlatformUnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
