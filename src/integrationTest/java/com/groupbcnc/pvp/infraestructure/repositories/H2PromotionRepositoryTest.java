package com.groupbcnc.pvp.infraestructure.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import com.groupbcnc.Application;
import com.groupbcnc.pvp.domain.exceptions.PromotionNotFoundException;
import com.groupbcnc.pvp.domain.models.Promotion;
import com.groupbcnc.pvp.domain.repositories.PromotionRepository;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext
public class H2PromotionRepositoryTest {

        @Autowired
        private PromotionRepository repository;

        private static final DateTimeFormatter SOME_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        private static final int SOME_PRODUCT_ID = 1;
        private static final int SOME_OTHER_PRODUCT_ID = 2;
        private static final int SOME_PROMOTION_ID = 1;
        private static final int SOME_PROMOTION_OFF = 10;
        private static final String SOME_PROMOTION_CODE_VALUE = "ff7ddd23-8e32-46fe-9de9-df01323f588b";
        private static final UUID SOME_PROMOTION_CODE = UUID.fromString(SOME_PROMOTION_CODE_VALUE);
        private static final int SOME_PRODUCT_PRIORITY = 1;
        private static final String SOME_PRODUCT_START_DATE_VALUE = "2023-02-01 00:00:00";
        private static final LocalDateTime SOME_PRODUCT_START_DATE = LocalDateTime.parse(SOME_PRODUCT_START_DATE_VALUE,
                        SOME_FORMATER);
        private static final String SOME_PRODUCT_END_DATE_VALUE = "2023-02-10 00:00:00";
        private static final LocalDateTime SOME_PRODUCT_END_DATE = LocalDateTime.parse(SOME_PRODUCT_END_DATE_VALUE,
                        SOME_FORMATER);
        private static final LocalDateTime SOME_DATE = LocalDateTime.parse("2023-02-07 00:00:00",
                        SOME_FORMATER);

        @Test
        @SqlGroup({
                        @Sql(statements = "INSERT INTO brands (id, code, name) VALUES (1, 'ff7ddd23-8e32-46fe-9de9-df01323f588b', 'Empresa 1');"),
                        @Sql(statements = "INSERT INTO products (id, code, brand_id, name) VALUES (" + SOME_PRODUCT_ID
                                        + ", 'e4ee2ddc-eec2-44c3-9d92-a0690e7f43a7', 1, 'camiseta 1');"),
                        @Sql(statements = "INSERT INTO prices (product_id, currency, price) VALUES (1, 'EUR', 12.2);"),
                        @Sql(statements = "INSERT INTO promotions (id, code, off, product_id, priority, start_date, end_date) VALUES ("
                                        + SOME_PROMOTION_ID
                                        + ", '" + SOME_PROMOTION_CODE_VALUE
                                        + "', " + SOME_PROMOTION_OFF
                                        + ", " + SOME_PRODUCT_ID
                                        + ", " + SOME_PRODUCT_PRIORITY
                                        + ", '" + SOME_PRODUCT_START_DATE_VALUE + "', '" + SOME_PRODUCT_END_DATE_VALUE
                                        + "');")
        })

        public void returnPromotionWhenExist() throws PromotionNotFoundException {
                Promotion promotion = repository.getPromotionByProductCodeAndDate(SOME_PRODUCT_ID, SOME_DATE);

                assertEquals(SOME_PROMOTION_ID, promotion.getId());
                assertEquals(SOME_PROMOTION_CODE, promotion.getCode());
                assertEquals(SOME_PROMOTION_OFF, promotion.getOff());
                assertEquals(SOME_PRODUCT_ID, promotion.getProductId());
                assertEquals(SOME_PRODUCT_PRIORITY, promotion.getPriority());
                assertEquals(SOME_PRODUCT_START_DATE, promotion.getStartDate());
                assertEquals(SOME_PRODUCT_END_DATE, promotion.getEndDate());

        }

        @Test
        public void throwPromotionNotFoundExceptionWhenPromotionNotFound() {

                assertThrows(PromotionNotFoundException.class, () -> {
                        repository.getPromotionByProductCodeAndDate(SOME_OTHER_PRODUCT_ID, SOME_DATE);
                }, "No promotion found for product " + SOME_OTHER_PRODUCT_ID);
        }
}
