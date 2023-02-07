package com.groupbcnc.pvp.infraestructure.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Currency;
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
import com.groupbcnc.pvp.domain.exceptions.ProductNotFoundException;
import com.groupbcnc.pvp.domain.models.Product;
import com.groupbcnc.pvp.domain.repositories.ProductRepository;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext
public class H2ProductRepositoryTest {

        @Autowired
        private ProductRepository repository;

        private static final int SOME_PRODUCT_ID = 1;
        private static final String SOME_PRODUCT_CODE_VALUE = "e4ee2ddc-eec2-44c3-9d92-a0690e7f43a7";
        private static final UUID SOME_PRODUCT_CODE = UUID.fromString(SOME_PRODUCT_CODE_VALUE);
        private static final String SOME_OTHER_PRODUCT_CODE_VALUE = "0c3ca372-7ebd-45ca-876a-6f881613d4d3";
        private static final UUID SOME_OTHER_PRODUCT_CODE = UUID.fromString(SOME_OTHER_PRODUCT_CODE_VALUE);
        private static final String SOME_BRAND_CODE_VALUE = "ff7ddd23-8e32-46fe-9de9-df01323f588b";
        private static final UUID SOME_BRAND_CODE = UUID.fromString(SOME_BRAND_CODE_VALUE);
        private static final int SOME_BRAND_ID = 1;
        private static final String SOME_PRODUCT_NAME = "camiseta 1";
        private static final float SOME_PRODUCT_PRICE = 12.2f;
        private static final String SOME_PRODUCT_CURRENCY_VALUE = "EUR";
        private static final Currency SOME_PRODUCT_CURRENCY = Currency.getInstance(SOME_PRODUCT_CURRENCY_VALUE);

        @Test
        @SqlGroup({
                        @Sql(statements = "INSERT INTO brands (id, code, name) " +
                                        "VALUES (" + SOME_BRAND_ID
                                        + ", '" + SOME_BRAND_CODE_VALUE + "', 'Empresa 1');"),
                        @Sql(statements = "INSERT INTO products (id, code, brand_id, name) " +
                                        "VALUES (" + SOME_PRODUCT_ID + ", '" + SOME_PRODUCT_CODE_VALUE + "', 1, '"
                                        + SOME_PRODUCT_NAME + "');"),
                        @Sql(statements = "INSERT INTO prices (product_id, currency, price) VALUES (" + SOME_BRAND_ID
                                        + ", '"
                                        + SOME_PRODUCT_CURRENCY_VALUE + "', "
                                        + SOME_PRODUCT_PRICE + ");")
        })
        public void returnProductWhenExist() throws ProductNotFoundException {
                Product product = repository.getProductByProductCodeAndBrandCode(
                                SOME_BRAND_CODE,
                                SOME_PRODUCT_CODE,
                                SOME_PRODUCT_CURRENCY_VALUE);

                assertEquals(SOME_PRODUCT_ID, product.getId());
                assertEquals(SOME_PRODUCT_CODE, product.getCode());
                assertEquals(SOME_BRAND_ID, product.getBrandId());
                assertEquals(SOME_PRODUCT_NAME, product.getName());
                assertEquals(SOME_PRODUCT_PRICE, product.getPrice().getValue());
                assertEquals(SOME_PRODUCT_CURRENCY, product.getPrice().getCurrency());
        }

        @Test
        public void throwProductNotFoundExceptionWhenProductNotFound() throws ProductNotFoundException {

                assertThrows(ProductNotFoundException.class, () -> {
                        repository.getProductByProductCodeAndBrandCode(
                                        SOME_BRAND_CODE,
                                        SOME_OTHER_PRODUCT_CODE,
                                        SOME_PRODUCT_CURRENCY_VALUE);
                }, "The product " + SOME_PRODUCT_CODE + " has not been found");
        }
}
