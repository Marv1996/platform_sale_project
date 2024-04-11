package com.sale.common.exceptions;

public abstract class PlatformApiException extends Exception {

    public PlatformApiException(String errorMessage) {
        super(errorMessage);
    }
}
