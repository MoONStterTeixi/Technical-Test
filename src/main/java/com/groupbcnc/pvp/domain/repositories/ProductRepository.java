package com.groupbcnc.pvp.domain.repositories;

import java.util.UUID;

import com.groupbcnc.pvp.domain.exceptions.ProductNotFoundException;
import com.groupbcnc.pvp.domain.models.Product;

public interface ProductRepository {
    public Product getProductByProductCodeAndBrandCode(UUID brandCode, UUID productCode, String currency)
            throws ProductNotFoundException;
}
