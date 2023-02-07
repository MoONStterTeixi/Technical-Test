package com.groupbcnc.pvp.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

public class Promotion {
    private @Getter int id;
    private @Getter UUID code;
    private @Getter int off;
    private @Getter int productId;
    private @Getter int priority;
    private @Getter LocalDateTime startDate;
    private @Getter LocalDateTime endDate;

    public Promotion(int id, UUID code, int productId, int off, int priority, LocalDateTime startDate,
            LocalDateTime endDate) {
        this.id = id;
        this.code = code;
        this.productId = productId;
        this.off = off;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
