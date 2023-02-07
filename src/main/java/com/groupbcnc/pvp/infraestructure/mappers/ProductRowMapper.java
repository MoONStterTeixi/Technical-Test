package com.groupbcnc.pvp.infraestructure.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import com.groupbcnc.pvp.domain.models.Price;
import com.groupbcnc.pvp.domain.models.Product;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    @Nullable
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getInt("id"),
                UUID.fromString(rs.getString("code")),
                rs.getInt("brand_id"),
                rs.getString("name"),
                new Price(Currency.getInstance(rs.getString("currency")), rs.getFloat("price")));
    }
}
