package com.sale.exceptions.carstatementexceptions;

import com.sale.common.exceptions.PlatformApiException;

public class CarStatementApiException extends PlatformApiException {
    public CarStatementApiException(String errorMessage) {
        super(errorMessage);
    }
}
