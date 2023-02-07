package com.groupbcnc.pvp.application.models;

import java.time.LocalDateTime;
import lombok.Getter;

public class GetPvpCommandResult {
    private @Getter float price;
    private @Getter int offApplied;
    private @Getter float pvp;
    private @Getter String promotionCode;
    private @Getter LocalDateTime applicationTime;

    public GetPvpCommandResult(float price, int offApplied, float pvp, String promotionCode,
            LocalDateTime applicationTime) {
        this.price = price;
        this.offApplied = offApplied;
        this.pvp = pvp;
        this.promotionCode = promotionCode;
        this.applicationTime = applicationTime;
    }
}
