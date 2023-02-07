package com.groupbcnc.pvp.application.models;

import java.util.Date;
import lombok.Getter;

public class GetPvpCommandResult {
    private @Getter float price;
    private @Getter float offApplied;
    private @Getter float pvp;
    private @Getter String promotionCode;
    private @Getter Date applicationTime;

    public GetPvpCommandResult(float price, float offApplied, float pvp, String promotionCode, Date applicationTime) {
        this.price = price;
        this.offApplied = offApplied;
        this.pvp = pvp;
        this.promotionCode = promotionCode;
        this.applicationTime = applicationTime;
    }
}
