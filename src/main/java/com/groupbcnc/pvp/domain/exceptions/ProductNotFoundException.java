package com.groupbcnc.pvp.domain.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(UUID productCode) {
        super("The product " + productCode + " has not been found");
    }

}
