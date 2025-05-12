package com.jmt.challengeindtx.infrastructure.shared;

public enum Currency {
    EUR("EUR"), USD("USD");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
