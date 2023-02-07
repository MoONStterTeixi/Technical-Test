package com.groupbcnc.pvp.infraestructure.repositories;

import java.time.LocalDateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.groupbcnc.pvp.domain.exceptions.PromotionNotFoundException;
import com.groupbcnc.pvp.domain.models.Promotion;
import com.groupbcnc.pvp.domain.repositories.PromotionRepository;
import com.groupbcnc.pvp.infraestructure.mappers.PromotionRowMapper;

public class H2PromotionRepository implements PromotionRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2PromotionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Promotion getPromotionByProductCodeAndDate(int productId, LocalDateTime applicationTime)
            throws PromotionNotFoundException {
        String query = "SELECT * " +
                "FROM promotions " +
                "WHERE product_id = ? " +
                "AND ? BETWEEN start_date AND end_date " +
                "ORDER BY priority DESC " +
                "LIMIT 1;";
        try {
            return jdbcTemplate.queryForObject(
                    query,
                    new PromotionRowMapper(),
                    new Object[] { productId, applicationTime });
        } catch (EmptyResultDataAccessException ex) {
            throw new PromotionNotFoundException(productId);
        }

    }

}
