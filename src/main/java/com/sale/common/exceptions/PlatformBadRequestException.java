package com.sale.common.exceptions;

public abstract class PlatformBadRequestException extends RuntimeException {
    public PlatformBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
