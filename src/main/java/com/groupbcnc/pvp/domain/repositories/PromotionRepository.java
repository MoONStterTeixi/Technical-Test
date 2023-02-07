package com.groupbcnc.pvp.domain.repositories;

import java.time.LocalDateTime;
import com.groupbcnc.pvp.domain.exceptions.PromotionNotFoundException;
import com.groupbcnc.pvp.domain.models.Promotion;

public interface PromotionRepository {
    public Promotion getPromotionByProductCodeAndDate(int productId, LocalDateTime applicationTime)
            throws PromotionNotFoundException;
}
