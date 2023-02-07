package com.groupbcnc.pvp.domain.models;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;

public class Promotion {
    private @Getter int id;
    private @Getter UUID code;
    private @Getter int off;
    private @Getter int productId;
    private @Getter int priority;
    private @Getter Date startDate;
    private @Getter Date endDate;

    public Promotion(int id, UUID code, int productId, int off, int priority, Date startDate, Date endDate) {
        this.id = id;
        this.code = code;
        this.productId = productId;
        this.off = off;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
