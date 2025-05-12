package com.jmt.challengeindtx.domain;


import com.jmt.challengeindtx.application.service.PriceService;
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
public class DomainPriceServiceUnitTest {

    private PriceRepository priceRepository;
    private PriceService tested;

    @BeforeEach
    void setup() {
        priceRepository = mock(PriceRepository.class);
        tested = new PriceService(priceRepository);
    }

    @Test
    @DisplayName("TestCase1_whenDateInJune14At10AM_thenGetAppliedPriceByBrandAndDateReturnsPriceList1")
    void whenDateInJune14At10AM_thenGetAppliedPriceByBrandAndDateReturnsPriceList1() {

        Price price = new Price(0L, 1, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 1L, 35455L, 1, BigDecimal.valueOf(35.50), "EUR");

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, LocalDateTime.parse("2020-06-14T10:00:00"), 1))
                .thenReturn(List.of(price));

        Price priceEval = tested.getAppliedPriceByBrandAndDate(1L, 35455L, LocalDateTime.parse("2020-06-14T10:00:00"));

        Assertions.assertNotNull(priceEval);
        Assertions.assertInstanceOf(Price.class, priceEval);
        Assertions.assertEquals(BigDecimal.valueOf(35.50), priceEval.getPrice());
        Assertions.assertEquals(35455L, priceEval.getProductId());
        Assertions.assertEquals(1L, priceEval.getBrandId());
        Assertions.assertEquals(1L, priceEval.getPriceListId());

        Assertions.assertEquals(LocalDateTime.parse("2020-06-14T00:00:00"), priceEval.getStartDate());
        Assertions.assertEquals(LocalDateTime.parse("2020-12-31T23:59:59"), priceEval.getEndDate());

    }

    @Test
    @DisplayName("TestCase2_whenDateInJune14At04PM_thenGetAppliedPriceByBrandAndDateReturnsPriceList2")
    void whenDateInJune14At04PM_thenGetAppliedPriceByBrandAndDateReturnsPriceList2() {

        Price price0 = new Price(0L, 1, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 1L, 35455L, 0, BigDecimal.valueOf(35.50), "EUR");
        Price price1 = new Price(1L, 1, LocalDateTime.parse("2020-06-14T15:00:00"), LocalDateTime.parse("2020-06-14T18:30:00"), 2L, 35455L, 1, BigDecimal.valueOf(25.45), "EUR");
        List<Price> priceList = List.of(price0, price1);

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, LocalDateTime.parse("2020-06-14T16:00:00"), 1L))
                .thenReturn((List<Price>) priceList);


        Price priceEval = tested.getAppliedPriceByBrandAndDate(1L, 35455L, LocalDateTime.parse("2020-06-14T16:00:00"));

        Assertions.assertNotNull(priceEval);
        Assertions.assertInstanceOf(Price.class, priceEval);
        Assertions.assertEquals(BigDecimal.valueOf(25.45), priceEval.getPrice());
        Assertions.assertEquals(35455L, priceEval.getProductId());
        Assertions.assertEquals(1L, priceEval.getBrandId());
        Assertions.assertEquals(2L, priceEval.getPriceListId());

        Assertions.assertEquals(LocalDateTime.parse("2020-06-14T15:00:00"), priceEval.getStartDate());
        Assertions.assertEquals(LocalDateTime.parse("2020-06-14T18:30:00"), priceEval.getEndDate());

    }

    @Test
    @DisplayName("TestCase3_whenDateInJune14At09PM_thenGetAppliedPriceByBrandAndDateReturnsPriceList1")
    void whenDateInJune14At09PM_thenGetAppliedPriceByBrandAndDateReturnsPriceList1() {

        Price price0 = new Price(0L, 1, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 1L, 35455L, 0, BigDecimal.valueOf(35.50), "EUR");
        List<Price> priceList = List.of(price0);

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, LocalDateTime.parse("2020-06-14T21:00:00"), 1L))
                .thenReturn((List<Price>) priceList);


        Price priceEval = tested.getAppliedPriceByBrandAndDate(1L, 35455L, LocalDateTime.parse("2020-06-14T21:00:00"));

        Assertions.assertNotNull(priceEval);
        Assertions.assertInstanceOf(Price.class, priceEval);
        Assertions.assertEquals(BigDecimal.valueOf(35.50), priceEval.getPrice());
        Assertions.assertEquals(35455L, priceEval.getProductId());
        Assertions.assertEquals(1L, priceEval.getBrandId());
        Assertions.assertEquals(1L, priceEval.getPriceListId());

        Assertions.assertEquals(LocalDateTime.parse("2020-06-14T00:00:00"), priceEval.getStartDate());
        Assertions.assertEquals(LocalDateTime.parse("2020-12-31T23:59:59"), priceEval.getEndDate());
    }

    @Test
    @DisplayName("TestCase4_whenDateInJune15At10AM_thenGetAppliedPriceByBrandAndDateReturnsPriceList3")
    void whenDateInJune15At10AM_thenGetAppliedPriceByBrandAndDateReturnsPriceList3() {

        Price price = new Price(2L, 1, LocalDateTime.parse("2020-06-15T00:00:00"), LocalDateTime.parse("2020-06-15T11:00:00"), 3L, 35455L, 1, BigDecimal.valueOf(30.50), "EUR");

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, LocalDateTime.parse("2020-06-15T10:00:00"), 1L))
                .thenReturn(List.of(price));


        Price priceEval = tested.getAppliedPriceByBrandAndDate(1L, 35455L, LocalDateTime.parse("2020-06-15T10:00:00"));

        Assertions.assertNotNull(priceEval);
        Assertions.assertInstanceOf(Price.class, priceEval);
        Assertions.assertEquals(BigDecimal.valueOf(30.50), priceEval.getPrice());
        Assertions.assertEquals(35455L, priceEval.getProductId());
        Assertions.assertEquals(1L, priceEval.getBrandId());
        Assertions.assertEquals(3L, priceEval.getPriceListId());

        Assertions.assertEquals(LocalDateTime.parse("2020-06-15T00:00:00"), priceEval.getStartDate());
        Assertions.assertEquals(LocalDateTime.parse("2020-06-15T11:00:00"), priceEval.getEndDate());
    }

    @Test
    @DisplayName("TestCase5_whenDateInJune16At09PM_thenGetAppliedPriceByBrandAndDateReturnsPriceList4")
    void whenDateInJune16At09PM_thenGetAppliedPriceByBrandAndDateReturnsPriceList4() {

        Integer productId = 35455;
        Price price0 = new Price(0L, 1, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 1L, productId.longValue(), 0, BigDecimal.valueOf(35.50), "EUR");
        Price price1 = new Price(3L, 1, LocalDateTime.parse("2020-06-15T16:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 4L, productId.longValue(), 1, BigDecimal.valueOf(38.95), "EUR");

        when(priceRepository.getPriceByProductIdAndDateAndBrand(35455L, LocalDateTime.parse("2020-06-16T21:00:00"), 1L))
                .thenReturn(List.of(price0, price1));


        Price priceEval = tested.getAppliedPriceByBrandAndDate(1L, 35455L, LocalDateTime.parse("2020-06-16T21:00:00"));

        Assertions.assertNotNull(priceEval);
        Assertions.assertInstanceOf(Price.class, priceEval);
        Assertions.assertEquals(BigDecimal.valueOf(38.95), priceEval.getPrice());
        Assertions.assertEquals(35455L, priceEval.getProductId());
        Assertions.assertEquals(1L, priceEval.getBrandId());
        Assertions.assertEquals(4L, priceEval.getPriceListId());

        Assertions.assertEquals(LocalDateTime.parse("2020-06-15T16:00:00"), priceEval.getStartDate());
        Assertions.assertEquals(LocalDateTime.parse("2020-12-31T23:59:59"), priceEval.getEndDate());
    }
}
