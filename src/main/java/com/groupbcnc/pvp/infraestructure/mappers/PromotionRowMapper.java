package com.groupbcnc.pvp.infraestructure.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import com.groupbcnc.pvp.domain.models.Promotion;

public class PromotionRowMapper implements RowMapper<Promotion> {

    @Override
    public Promotion mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Promotion(
                rs.getInt("id"),
                UUID.fromString(rs.getString("code")),
                rs.getInt("product_id"),
                rs.getInt("off"),
                rs.getInt("priority"),
                rs.getDate("start_date"),
                rs.getDate("end_date"));
    }

}
