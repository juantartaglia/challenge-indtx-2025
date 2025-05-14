package com.jmt.challengeindtx.application.service;


import com.jmt.challengeindtx.domain.model.Price;
import com.jmt.challengeindtx.domain.port.out.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class PriceServiceTest {

    private PriceRepository priceRepository;
    private PriceService tested;

    @BeforeEach
    void setup() {
        priceRepository = mock(PriceRepository.class);
        tested = new PriceService(priceRepository);
    }

    private Price createPrice(long id, int brandId, String start, String end, long priceListId,
                              long productId, int priority, double price, String currency) {
        return new Price(
                id,
                brandId,
                LocalDateTime.parse(start),
                LocalDateTime.parse(end),
                priceListId,
                productId,
                priority,
                BigDecimal.valueOf(price),
                currency
        );
    }

    private void assertValidPrice(Price price, long expectedProductId, long expectedBrandId,
                                  long expectedPriceListId, double expectedPrice,
                                  String expectedStart, String expectedEnd) {
        Assertions.assertNotNull(price);
        Assertions.assertEquals(expectedProductId, price.getProductId());
        Assertions.assertEquals(expectedBrandId, price.getBrandId());
        Assertions.assertEquals(expectedPriceListId, price.getPriceListId());
        Assertions.assertEquals(BigDecimal.valueOf(expectedPrice), price.getPrice());
        Assertions.assertEquals(LocalDateTime.parse(expectedStart), price.getStartDate());
        Assertions.assertEquals(LocalDateTime.parse(expectedEnd), price.getEndDate());
    }

    @Test
    @DisplayName("Should return price list 1 for June 14th at 10:00 AM")
    void shouldReturnPriceList1_ForJune14At10AM() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-14T10:00:00");
        Price price = createPrice(0L, 1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1L, 35455L, 1, 35.50, "EUR");

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, queryDate, 1L)).thenReturn(List.of(price));

        Price result = tested.getAppliedPriceByBrandAndDate(1L, 35455L, queryDate);

        assertValidPrice(result, 35455L, 1L, 1L, 35.50, "2020-06-14T00:00:00", "2020-12-31T23:59:59");
    }

    @Test
    @DisplayName("Should return price list 2 (higher priority) for June 14th at 4:00 PM")
    void shouldReturnPriceList2_ForJune14At4PM() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-14T16:00:00");

        Price lowPriority = createPrice(0L, 1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1L, 35455L, 0, 35.50, "EUR");
        Price highPriority = createPrice(1L, 1, "2020-06-14T15:00:00", "2020-06-14T18:30:00", 2L, 35455L, 1, 25.45, "EUR");

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, queryDate, 1L)).thenReturn(List.of(lowPriority, highPriority));

        Price result = tested.getAppliedPriceByBrandAndDate(1L, 35455L, queryDate);

        assertValidPrice(result, 35455L, 1L, 2L, 25.45, "2020-06-14T15:00:00", "2020-06-14T18:30:00");
    }

    @Test
    @DisplayName("Should return price list 1 for June 14th at 9:00 PM")
    void shouldReturnPriceList1_ForJune14At9PM() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-14T21:00:00");
        Price price = createPrice(0L, 1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1L, 35455L, 1, 35.50, "EUR");

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, queryDate, 1L)).thenReturn(List.of(price));

        Price result = tested.getAppliedPriceByBrandAndDate(1L, 35455L, queryDate);

        assertValidPrice(result, 35455L, 1L, 1L, 35.50, "2020-06-14T00:00:00", "2020-12-31T23:59:59");
    }

    @Test
    @DisplayName("Should return price list 3 for June 15th at 10:00 AM")
    void shouldReturnPriceList3_ForJune15At10AM() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-15T10:00:00");
        Price price = createPrice(2L, 1, "2020-06-15T00:00:00", "2020-06-15T11:00:00", 3L, 35455L, 1, 30.50, "EUR");

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, queryDate, 1L)).thenReturn(List.of(price));

        Price result = tested.getAppliedPriceByBrandAndDate(1L, 35455L, queryDate);

        assertValidPrice(result, 35455L, 1L, 3L, 30.50, "2020-06-15T00:00:00", "2020-06-15T11:00:00");
    }

    @Test
    @DisplayName("Should return price list 4 for June 16th at 9:00 AM")
    void shouldReturnPriceList4_ForJune16At9AM() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-16T09:00:00");
        Price price = createPrice(3L, 1, "2020-06-15T16:00:00", "2020-12-31T23:59:59", 4L, 35455L, 1, 38.95, "EUR");

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, queryDate, 1L)).thenReturn(List.of(price));

        Price result = tested.getAppliedPriceByBrandAndDate(1L, 35455L, queryDate);

        assertValidPrice(result, 35455L, 1L, 4L, 38.95, "2020-06-15T16:00:00", "2020-12-31T23:59:59");
    }
}
