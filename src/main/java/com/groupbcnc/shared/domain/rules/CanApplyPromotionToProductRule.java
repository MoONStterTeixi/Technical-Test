package com.groupbcnc.shared.domain.rules;

import java.time.LocalDateTime;
import com.groupbcnc.pvp.domain.models.Product;
import com.groupbcnc.pvp.domain.models.Promotion;
import com.groupbcnc.shared.domain.exceptions.ViolatedRuleException;

public class CanApplyPromotionToProductRule {

    private CanApplyPromotionToProductRule() {
    }

    public static void validate(Product product, Promotion promotion, LocalDateTime applicationTime)
            throws ViolatedRuleException {
        if (product.getId() != promotion.getProductId()) {
            throw new ViolatedRuleException(
                    "Cant apply the promotion " + promotion.getCode() + " to the product " + product.getCode());
        }
        if (!(applicationTime.isAfter(promotion.getStartDate()) && applicationTime.isBefore(promotion.getEndDate()))) {
            throw new ViolatedRuleException(
                    "Cant apply the promotion " + promotion.getCode() + "to the roduct " + product.getCode()
                            + " was expired");
        }
    }
}
