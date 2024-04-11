package com.sale.exceptions.statementexceptions;

import com.sale.common.exceptions.PlatformBadRequestException;

public class StatementBadRequestException extends PlatformBadRequestException {
    public StatementBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
