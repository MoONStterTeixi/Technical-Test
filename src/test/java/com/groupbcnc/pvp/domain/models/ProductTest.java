package com.groupbcnc.pvp.domain.models;

import java.util.Date;
import java.text.ParseException;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.groupbcnc.shared.domain.exceptions.ViolatedRuleException;

public class ProductTest {

    private static final UUID SOME_PRODUCT_CODE = UUID.randomUUID();
    private static final UUID SOME_PROMOTION_CODE = UUID.randomUUID();
    private static final int SOME_PRODUCT_ID = 1;
    private static final int SOME_OTHER_PRODUCT_ID = 2;
    private static final int SOME_BRAND_ID = 1;
    private static final int SOME_PROMOTION_ID = 1;
    private static Date SOME_PROMOTION_START_DATE = Date.from(Instant.parse("2023-02-01T00:00:00Z"));
    private static Date SOME_PROMOTION_END_DATE = Date.from(Instant.parse("2023-02-10T00:00:00Z"));
    private static Date SOME_IN_RANGE_DATE = Date.from(Instant.parse("2023-02-05T00:00:00Z"));
    private static Date SOME_OUT_RANGE_DATE = Date.from(Instant.parse("2023-02-12T00:00:00Z"));
    private static final float SOME_PRICE_VALUE = 423f;
    private static final float SOME_PVP_VALUE = 372.24f;
    private static final int SOME_OFF_APPLIED = 12;
    private static final int SOME_PRIORITY = 1;
    private static final Currency SOME_CURRENCY = Currency.getInstance("EUR");
    private static final String SOME_PRODUCT_NAME = "Product 1";
    private static final Price SOME_PRICE = new Price(SOME_CURRENCY, SOME_PRICE_VALUE);
    private static final Product SOME_PRODUCT = new Product(SOME_PRODUCT_ID, SOME_PRODUCT_CODE, SOME_BRAND_ID,
            SOME_PRODUCT_NAME, SOME_PRICE);
    private static final Promotion SOME_PROMOTION = new Promotion(SOME_PROMOTION_ID, SOME_PROMOTION_CODE,
            SOME_PRODUCT_ID, SOME_OFF_APPLIED, SOME_PRIORITY, SOME_PROMOTION_START_DATE,
            SOME_PROMOTION_END_DATE);
    private static final Promotion SOME_OTHER_PROMOTION = new Promotion(SOME_PROMOTION_ID, SOME_PROMOTION_CODE,
            SOME_OTHER_PRODUCT_ID, SOME_OFF_APPLIED, SOME_PRIORITY, SOME_PROMOTION_START_DATE,
            SOME_PROMOTION_END_DATE);

    @Test
    public void throwInvalidPromotionWhenPromotionIsForOtherProduct() {

        assertThrows(ViolatedRuleException.class, () -> {
            SOME_PRODUCT.apply(SOME_OTHER_PROMOTION, SOME_IN_RANGE_DATE);
        }, "Cant apply the promotion " + SOME_PROMOTION_CODE + " to the product " + SOME_PRODUCT_CODE);
    }

    @Test
    public void throwInvalidPromotionWhenPromotionWasExpired() throws ParseException {

        assertThrows(ViolatedRuleException.class, () -> {
            SOME_PRODUCT.apply(SOME_PROMOTION, SOME_OUT_RANGE_DATE);
        }, "Cant apply the promotion " + SOME_PROMOTION_CODE + "to the roduct " +
                SOME_PRODUCT_CODE + " was expired");

    }

    @Test
    public void getPvpWhenApplyPromotion() throws ViolatedRuleException {
        Pvp pvp = SOME_PRODUCT.apply(SOME_PROMOTION, SOME_IN_RANGE_DATE);

        assertEquals(SOME_PRICE_VALUE, pvp.getPrice());
        assertEquals(SOME_OFF_APPLIED, pvp.getOffApplied());
        assertEquals(SOME_PVP_VALUE, pvp.getPvp());
        assertEquals(SOME_PROMOTION_CODE, pvp.getPromotionCode());
    }
}
