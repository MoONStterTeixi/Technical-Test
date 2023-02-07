package com.groupbcnc.pvp.infraestructure.repositories;

import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.groupbcnc.pvp.domain.exceptions.ProductNotFoundException;
import com.groupbcnc.pvp.domain.models.Product;
import com.groupbcnc.pvp.domain.repositories.ProductRepository;
import com.groupbcnc.pvp.infraestructure.mappers.ProductRowMapper;

public class H2ProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product getProductByProductCodeAndBrandCode(UUID brandCode, UUID productCode, String currency)
            throws ProductNotFoundException {
        String query = "SELECT products.id, products.code, products.brand_id, products.name, prices.currency, prices.price "
                +
                "FROM products " +
                "LEFT JOIN brands ON products.brand_id = brands.id " +
                "LEFT JOIN prices ON products.id = prices.product_id " +
                "WHERE brands.code = ? AND products.code = ? AND prices.currency = ?;";
        try {
            return jdbcTemplate.queryForObject(
                    query,
                    new ProductRowMapper(),
                    new Object[] { brandCode, productCode, currency });
        } catch (EmptyResultDataAccessException ex) {
            throw new ProductNotFoundException(productCode);
        }

    }

}
