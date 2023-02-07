package com.groupbcnc.pvp.application.models;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

import lombok.Getter;

public class GetPvpCommand {
    private @Getter UUID brandCode;
    private @Getter UUID productCode;
    private @Getter Currency currency;
    private @Getter LocalDateTime currentTime;

    public GetPvpCommand(UUID brandCode, UUID productCode, Currency currency, LocalDateTime currentTime) {
        this.brandCode = brandCode;
        this.productCode = productCode;
        this.currency = currency;
        this.currentTime = currentTime;
    }
}
