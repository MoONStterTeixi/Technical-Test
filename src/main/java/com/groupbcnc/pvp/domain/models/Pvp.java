package com.groupbcnc.pvp.domain.models;

import lombok.Getter;
import java.util.UUID;

public class Pvp {
    private @Getter float price;
    private @Getter int offApplied;
    private @Getter float pvp;
    private @Getter UUID promotionCode;

    public Pvp(float price, int offApplied, float pvp, UUID promotionCode) {
        this.price = price;
        this.offApplied = offApplied;
        this.pvp = pvp;
        this.promotionCode = promotionCode;
    }
}
