package com.groupbcnc.pvp.infraestructure.configurations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.groupbcnc.pvp.domain.repositories.ProductRepository;
import com.groupbcnc.pvp.domain.repositories.PromotionRepository;
import com.groupbcnc.pvp.infraestructure.repositories.H2ProductRepository;
import com.groupbcnc.pvp.infraestructure.repositories.H2PromotionRepository;

@Configuration
public class RepositoriesConfiguration {

    @Bean
    public ProductRepository productRepository(JdbcTemplate jdbcTemplate) {
        return new H2ProductRepository(jdbcTemplate);
    }

    @Bean
    public PromotionRepository promotionRepository(JdbcTemplate jdbcTemplate){
        return new H2PromotionRepository(jdbcTemplate);
    }
}
