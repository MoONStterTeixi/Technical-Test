package com.groupbcnc.pvp.domain.models;

import java.util.Currency;

import lombok.Getter;

public class Price {

    private @Getter Currency currency;
    private @Getter float value;

    public Price(Currency currency, float value) {
        this.currency = currency;
        this.value = value;
    }
}
