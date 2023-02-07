package com.groupbcnc.pvp.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;
import com.groupbcnc.shared.domain.exceptions.ViolatedRuleException;
import com.groupbcnc.shared.domain.rules.CanApplyPromotionToProductRule;

import lombok.Getter;

public class Product {

    private @Getter int id;
    private @Getter UUID code;
    private @Getter int brandId;
    private @Getter String name;
    private @Getter Price price;

    public Product(int id, UUID code, int brandId, String name, Price price) {
        this.id = id;
        this.code = code;
        this.brandId = brandId;
        this.name = name;
        this.price = price;
    }

    public Pvp apply(Promotion promotion, LocalDateTime applicationTime) throws ViolatedRuleException {
        CanApplyPromotionToProductRule.validate(this, promotion, applicationTime);
        return new Pvp(
                this.price.getValue(),
                promotion.getOff(),
                Float.valueOf(
                        this.price.getValue() - (float) (promotion.getOff() / 100.0) *
                                this.price.getValue()),
                promotion.getCode());
    }

}
