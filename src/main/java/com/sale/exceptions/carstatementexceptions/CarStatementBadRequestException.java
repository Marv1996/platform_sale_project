package com.sale.exceptions.carstatementexceptions;

import com.sale.common.exceptions.PlatformBadRequestException;

public class CarStatementBadRequestException extends PlatformBadRequestException {
    public CarStatementBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
