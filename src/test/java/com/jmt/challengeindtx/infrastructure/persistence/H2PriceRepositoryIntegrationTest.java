package com.jmt.challengeindtx.infrastructure.persistence;

import com.jmt.challengeindtx.infrastructure.persistence.entity.PriceEntity;
import com.jmt.challengeindtx.infrastructure.persistence.repository.PriceH2Repository;
import com.jmt.challengeindtx.infrastructure.shared.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class H2PriceRepositoryIntegrationTest {

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;
    private static final String EUR = "EUR";

    @Autowired
    private PriceH2Repository underTest;

    private PriceEntity price0;
    private PriceEntity price1;
    private PriceEntity price2;
    private PriceEntity price3;

    @BeforeEach
    void setUp() {
        //given
        price0 = new PriceEntity(1,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                BRAND_ID, PRODUCT_ID, 0,
                BigDecimal.valueOf(35.50), Currency.EUR);
        price1 = new PriceEntity(1,
                LocalDateTime.parse("2020-06-14T15:00:00"),
                LocalDateTime.parse("2020-06-14T18:30:00"),
                BRAND_ID, PRODUCT_ID, 1,
                BigDecimal.valueOf(25.45), Currency.EUR);
        price2 = new PriceEntity(1,
                LocalDateTime.parse("2020-06-15T00:00:00"),
                LocalDateTime.parse("2020-06-15T11:00:00"),
                BRAND_ID, PRODUCT_ID, 1,
                BigDecimal.valueOf(30.50), Currency.EUR);
        price3 = new PriceEntity(1,
                LocalDateTime.parse("2020-06-15T16:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                BRAND_ID, PRODUCT_ID, 1,
                BigDecimal.valueOf(38.95), Currency.EUR);

        underTest.saveAll(List.of(price0, price1, price2, price3));
    }

    @Test
    @DisplayName("Should find two prices when date is in range of two prices")
    void shouldFindTwoPricesWhenDateIsInRangeOfTwoPrices() {
        //when
        List<PriceEntity> result = underTest.findByProductIdAndBrandIdAndDate(
                PRODUCT_ID, BRAND_ID, LocalDateTime.parse("2020-06-14T16:00:00"));

        //then
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(price0, price1);
    }

    @Test
    @DisplayName("Should find one price when date is in range of one price")
    void shouldFindOnePriceWhenDateIsInRangeOfOnePrice() {
        //when
        List<PriceEntity> result = underTest.findByProductIdAndBrandIdAndDate(
                PRODUCT_ID, BRAND_ID, LocalDateTime.parse("2020-06-14T10:00:00"));

        //then
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .containsExactly(price0);
    }

    @Test
    @DisplayName("Should return empty list when no prices exist for the date")
    void shouldReturnEmptyListWhenNoPricesExistForDate() {
        //when
        List<PriceEntity> result = underTest.findByProductIdAndBrandIdAndDate(
                PRODUCT_ID, BRAND_ID, LocalDateTime.parse("2020-06-13T10:00:00"));

        //then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Should return empty list when product does not exist")
    void shouldReturnEmptyListWhenProductDoesNotExist() {
        //when
        List<PriceEntity> result = underTest.findByProductIdAndBrandIdAndDate(
                99999L, BRAND_ID, LocalDateTime.parse("2020-06-14T16:00:00"));

        //then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Should return empty list when brand does not exist")
    void shouldReturnEmptyListWhenBrandDoesNotExist() {
        //when
        List<PriceEntity> result = underTest.findByProductIdAndBrandIdAndDate(
                PRODUCT_ID, 99999L, LocalDateTime.parse("2020-06-14T16:00:00"));

        //then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }
}
