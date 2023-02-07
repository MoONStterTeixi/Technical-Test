package com.groupbcnc.pvp.infraestructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.groupbcnc.pvp.application.services.GetPvpUseCase;
import com.groupbcnc.pvp.domain.repositories.ProductRepository;
import com.groupbcnc.pvp.domain.repositories.PromotionRepository;

@Configuration
public class ServicesConfiguration {

    @Bean
    public GetPvpUseCase getPvpUseCase(ProductRepository productRepository, PromotionRepository promotionRepository) {
        return new GetPvpUseCase(productRepository, promotionRepository);
    }
}
