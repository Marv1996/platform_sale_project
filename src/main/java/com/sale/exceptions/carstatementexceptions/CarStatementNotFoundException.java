package com.sale.exceptions.carstatementexceptions;

import com.sale.common.exceptions.PlatformNotFoundException;

public class CarStatementNotFoundException extends PlatformNotFoundException {
    public CarStatementNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
