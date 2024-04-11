package com.sale.common.util;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerate {

    /**
     * generate verification code
     *
     * @return verification code
     */
    public static String generateVerifyCode() {
        return RandomStringUtils.random(6, true, true);
    }
}
