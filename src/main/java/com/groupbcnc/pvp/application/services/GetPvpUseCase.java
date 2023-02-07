package com.groupbcnc.pvp.application.services;

import com.groupbcnc.pvp.application.models.GetPvpCommand;
import com.groupbcnc.pvp.application.models.GetPvpCommandResult;
import com.groupbcnc.pvp.domain.exceptions.ProductNotFoundException;
import com.groupbcnc.pvp.domain.exceptions.PromotionNotFoundException;
import com.groupbcnc.pvp.domain.models.Product;
import com.groupbcnc.pvp.domain.models.Promotion;
import com.groupbcnc.pvp.domain.models.Pvp;
import com.groupbcnc.pvp.domain.repositories.ProductRepository;
import com.groupbcnc.pvp.domain.repositories.PromotionRepository;
import com.groupbcnc.shared.domain.exceptions.ViolatedRuleException;

public class GetPvpUseCase {

    private static final int DEFAULT_OFF_APPLIED = 0;
    private static final String EMPTY_PROMOTION_CODE = "";

    private final ProductRepository productRepositor;
    private final PromotionRepository promotionRepository;

    public GetPvpUseCase(ProductRepository productRepositor, PromotionRepository promotionRepository) {
        this.productRepositor = productRepositor;
        this.promotionRepository = promotionRepository;
    }

    public GetPvpCommandResult execute(GetPvpCommand command) throws ProductNotFoundException, ViolatedRuleException {

        Product product = productRepositor.getProductByProductCodeAndBrandCode(
                command.getBrandCode(),
                command.getProductCode(),
                command.getCurrency().toString());
        try {
            Promotion promotion = promotionRepository.getPromotionByProductCodeAndDate(product.getId(),
                    command.getCurrentTime());

            Pvp pvp = product.apply(promotion, command.getCurrentTime());

            return resultWithPromotionApplied(pvp, command);
        } catch (PromotionNotFoundException ex) {
            return resultWithOutPromotionApplied(product, command);
        }
    }

    private GetPvpCommandResult resultWithPromotionApplied(Pvp pvp, GetPvpCommand command) {
        return new GetPvpCommandResult(
                pvp.getPrice(),
                pvp.getOffApplied(),
                pvp.getPvp(),
                pvp.getPromotionCode().toString(),
                command.getCurrentTime());
    }

    private GetPvpCommandResult resultWithOutPromotionApplied(Product product, GetPvpCommand command) {
        return new GetPvpCommandResult(
                product.getPrice().getValue(),
                DEFAULT_OFF_APPLIED,
                product.getPrice().getValue(),
                EMPTY_PROMOTION_CODE,
                command.getCurrentTime());
    }
}
