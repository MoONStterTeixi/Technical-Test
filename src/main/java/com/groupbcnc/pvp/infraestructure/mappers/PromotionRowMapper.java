package com.groupbcnc.pvp.infraestructure.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import com.groupbcnc.pvp.domain.models.Promotion;

public class PromotionRowMapper implements RowMapper<Promotion> {

    private static final DateTimeFormatter SOME_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Promotion mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Promotion(
                rs.getInt("id"),
                UUID.fromString(rs.getString("code")),
                rs.getInt("product_id"),
                rs.getInt("off"),
                rs.getInt("priority"),
                LocalDateTime.parse(rs.getString("start_date"), SOME_FORMATER),
                LocalDateTime.parse(rs.getString("end_date"), SOME_FORMATER));
    }

}
