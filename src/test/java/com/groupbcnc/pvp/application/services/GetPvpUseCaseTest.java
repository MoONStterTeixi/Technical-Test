package com.groupbcnc.pvp.application.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;
import com.groupbcnc.pvp.application.models.GetPvpCommand;
import com.groupbcnc.pvp.application.models.GetPvpCommandResult;
import com.groupbcnc.pvp.domain.exceptions.ProductNotFoundException;
import com.groupbcnc.pvp.domain.exceptions.PromotionNotFoundException;
import com.groupbcnc.pvp.domain.models.Price;
import com.groupbcnc.pvp.domain.models.Product;
import com.groupbcnc.pvp.domain.models.Promotion;
import com.groupbcnc.pvp.domain.repositories.ProductRepository;
import com.groupbcnc.pvp.domain.repositories.PromotionRepository;
import com.groupbcnc.shared.domain.exceptions.ViolatedRuleException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GetPvpUseCaseTest {

        private static final DateTimeFormatter SOME_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        private static final UUID SOME_BRAND_CODE = UUID.randomUUID();
        private static final UUID SOME_PRODUCT_CODE = UUID.randomUUID();
        private static final UUID SOME_PROMOTION_CODE = UUID.randomUUID();
        private static final int SOME_PRODUCT_ID = 1;
        private static final int SOME_BRAND_ID = 1;
        private static final int SOME_PROMOTION_ID = 1;
        private static final LocalDateTime SOME_TIME = LocalDateTime.now();
        private static LocalDateTime SOME_PROMOTION_START_DATE = LocalDateTime.parse("2023-02-01 00:00:00",
                        SOME_FORMATER);
        private static LocalDateTime SOME_PROMOTION_END_DATE = LocalDateTime.parse("2023-02-10 00:00:00",
                        SOME_FORMATER);
        private static final float SOME_PRICE_VALUE = 100f;
        private static final int SOME_OFF_APPLIED = 10;
        private static final int ZERO_OFF_APPLIED = 0;
        private static final float SOME_PVP = 90.00f;
        private static final int SOME_PRIORITY = 1;
        private static final Currency SOME_CURRENCY = Currency.getInstance("EUR");
        private static final String SOME_PRODUCT_NAME = "Product 1";
        private static final String SOME_EMPTY_PROMOTION_CODE = "";
        private static final Price SOME_PRICE = new Price(SOME_CURRENCY, SOME_PRICE_VALUE);
        private static final Product SOME_PRODUCT = new Product(SOME_PRODUCT_ID, SOME_PRODUCT_CODE, SOME_BRAND_ID,
                        SOME_PRODUCT_NAME, SOME_PRICE);
        private static final Promotion SOME_PROMOTION = new Promotion(SOME_PROMOTION_ID, SOME_PROMOTION_CODE,
                        SOME_PRODUCT_ID, SOME_OFF_APPLIED, SOME_PRIORITY, SOME_PROMOTION_START_DATE,
                        SOME_PROMOTION_END_DATE);

        private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        private final PromotionRepository promotionRepository = Mockito.mock(PromotionRepository.class);

        private final GetPvpUseCase useCase = new GetPvpUseCase(productRepository, promotionRepository);

        @Test
        public void getPvpFromProductWithPromotionAppliedWhenProductAndPromotionExistAndPromotionIsValid()
                        throws ProductNotFoundException, PromotionNotFoundException, ViolatedRuleException {
                GetPvpCommand command = new GetPvpCommand(SOME_BRAND_CODE, SOME_PRODUCT_CODE, SOME_CURRENCY, SOME_TIME);

                when(productRepository.getProductByProductCodeAndBrandCode(command.getBrandCode(),
                                command.getProductCode(),
                                command.getCurrency().toString())).thenReturn(SOME_PRODUCT);
                when(promotionRepository.getPromotionByProductCodeAndDate(SOME_PRODUCT_ID, SOME_TIME))
                                .thenReturn(SOME_PROMOTION);

                GetPvpCommandResult commandResult = useCase.execute(command);

                assertEquals(SOME_PRICE_VALUE, commandResult.getPrice());
                assertEquals(SOME_TIME, commandResult.getApplicationTime());
                assertEquals(SOME_OFF_APPLIED, commandResult.getOffApplied());
                assertEquals(SOME_PVP, commandResult.getPvp());
                assertEquals(SOME_PROMOTION_CODE.toString(), commandResult.getPromotionCode());
        }

        @Test
        public void getPvpFromProductWhenPromotionNotExist()
                        throws ProductNotFoundException, PromotionNotFoundException, ViolatedRuleException {
                GetPvpCommand command = new GetPvpCommand(SOME_BRAND_CODE, SOME_PRODUCT_CODE, SOME_CURRENCY, SOME_TIME);
                when(productRepository.getProductByProductCodeAndBrandCode(command.getBrandCode(),
                                command.getProductCode(),
                                command.getCurrency().toString())).thenReturn(SOME_PRODUCT);
                when(promotionRepository.getPromotionByProductCodeAndDate(SOME_PRODUCT_ID, SOME_TIME))
                                .thenThrow(PromotionNotFoundException.class);

                GetPvpCommandResult commandResult = useCase.execute(command);

                assertEquals(SOME_PRICE_VALUE, commandResult.getPrice());
                assertEquals(SOME_TIME, commandResult.getApplicationTime());
                assertEquals(ZERO_OFF_APPLIED, commandResult.getOffApplied());
                assertEquals(SOME_PRICE_VALUE, commandResult.getPvp());
                assertEquals(SOME_EMPTY_PROMOTION_CODE, commandResult.getPromotionCode());
        }

        @Test
        public void throwProductNotFoundExceptionWhenProductNotFound()
                        throws ProductNotFoundException, PromotionNotFoundException {
                GetPvpCommand command = new GetPvpCommand(SOME_BRAND_CODE, SOME_PRODUCT_CODE, SOME_CURRENCY, SOME_TIME);

                when(productRepository.getProductByProductCodeAndBrandCode(command.getBrandCode(),
                                command.getProductCode(),
                                command.getCurrency().toString())).thenThrow(ProductNotFoundException.class);

                assertThrows(ProductNotFoundException.class, () -> {
                        useCase.execute(command);
                }, "The product " + SOME_PRODUCT_CODE + " has not been found");
        }
}
