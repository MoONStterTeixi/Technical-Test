package com.groupbcnc.pvp.domain.repositories;

import java.util.Date;

import com.groupbcnc.pvp.domain.exceptions.PromotionNotFoundException;
import com.groupbcnc.pvp.domain.models.Promotion;

public interface PromotionRepository {
    public Promotion getPromotionByProductCodeAndDate(int productId, Date applicationTime)
            throws PromotionNotFoundException;
}
