package org.luisbaquiax.jwtcaseapi.utils;

import java.math.BigDecimal;

public enum EnumConfig {
    MINIMUM_PRICE(new BigDecimal("35.00"));

    private BigDecimal price;

    public BigDecimal getPrice() {
        return MINIMUM_PRICE.price;
    }

    EnumConfig(BigDecimal bigDecimal) {
        this.price = bigDecimal;
    }
}
