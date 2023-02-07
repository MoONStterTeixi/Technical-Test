package com.groupbcnc.pvp.domain.exceptions;

public class PromotionNotFoundException extends Exception {

    public PromotionNotFoundException(int productId) {
        super("No promotion found for product " + productId);
    }
}