package com.jmt.challengeindtx.infrastructure.web;

import com.jmt.challengeindtx.application.service.PriceService;
import com.jmt.challengeindtx.domain.model.Price;
import com.jmt.challengeindtx.domain.port.out.PriceRepository;
import com.jmt.challengeindtx.infrastructure.web.configuration.GlobalExceptionHandler;
import com.jmt.challengeindtx.infrastructure.web.mapper.PriceResponseMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
@Import({PriceService.class, PriceResponseMapperImpl.class, GlobalExceptionHandler.class})
class PriceControllerIntegrationTest {

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;
    private static final String BASE_URL = "/product/%s/price";
    private static final String APPLICATION_DATE_PARAM = "applicationDate";
    private static final String BRAND_ID_PARAM = "brandId";
    private static final String EUR = "EUR";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PriceRepository priceRepository;

    @Test
    @DisplayName("Should return 404 when no price is found for the given date")
    void shouldReturn404WhenNoPriceFoundForDate() throws Exception {
        when(priceRepository.getPriceByProductIdAndDateAndBrand(
            PRODUCT_ID, 
            LocalDateTime.parse("2021-01-10T10:00:00"), 
            BRAND_ID))
            .thenReturn(List.of());

        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(APPLICATION_DATE_PARAM, "2021-01-10T10:00:00")
                .param(BRAND_ID_PARAM, BRAND_ID.toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when product does not exist")
    void shouldReturn404WhenProductDoesNotExist() throws Exception {
        Long nonExistentProductId = 35456L;
        when(priceRepository.getPriceByProductIdAndDateAndBrand(
            nonExistentProductId, 
            LocalDateTime.parse("2020-06-14T21:00:00"), 
            BRAND_ID))
            .thenReturn(List.of());

        String url = String.format(BASE_URL, nonExistentProductId);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(APPLICATION_DATE_PARAM, "2020-06-14T21:00:00")
                .param(BRAND_ID_PARAM, BRAND_ID.toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 400 when application date is missing")
    void shouldReturn400WhenApplicationDateIsMissing() throws Exception {
        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(BRAND_ID_PARAM, BRAND_ID.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when brand ID is missing")
    void shouldReturn400WhenBrandIdIsMissing() throws Exception {
        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(APPLICATION_DATE_PARAM, "2020-06-14T21:00:00"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when both application date and brand ID are missing")
    void shouldReturn400WhenBothApplicationDateAndBrandIdAreMissing() throws Exception {
        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return price list 1 for June 14 at 10:00 AM")
    void shouldReturnPriceList1ForJune14At10AM() throws Exception {
        Price price = new Price(0L, 1, 
            LocalDateTime.parse("2020-06-14T00:00:00"), 
            LocalDateTime.parse("2020-12-31T23:59:59"), 
            1L, PRODUCT_ID, 1, 
            BigDecimal.valueOf(35.50), EUR);

        when(priceRepository.getPriceByProductIdAndDateAndBrand(
            PRODUCT_ID, 
            LocalDateTime.parse("2020-06-14T10:00:00"), 
            BRAND_ID))
            .thenReturn(List.of(price));

        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(APPLICATION_DATE_PARAM, "2020-06-14T10:00:00")
                .param(BRAND_ID_PARAM, BRAND_ID.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
            .andExpect(jsonPath("$.brandId").value(BRAND_ID))
            .andExpect(jsonPath("$.priceListId").value(1))
            .andExpect(jsonPath("$.dateRange.startDate").value("2020-06-14T00:00:00"))
            .andExpect(jsonPath("$.dateRange.endDate").value("2020-12-31T23:59:59"))
            .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Should return price list 2 for June 14 at 4:00 PM")
    void shouldReturnPriceList2ForJune14At4PM() throws Exception {
        Price price0 = new Price(0L, 1, 
            LocalDateTime.parse("2020-06-14T00:00:00"), 
            LocalDateTime.parse("2020-12-31T23:59:59"), 
            1L, PRODUCT_ID, 0, 
            BigDecimal.valueOf(35.50), EUR);
        Price price1 = new Price(1L, 1, 
            LocalDateTime.parse("2020-06-14T15:00:00"), 
            LocalDateTime.parse("2020-06-14T18:30:00"), 
            2L, PRODUCT_ID, 1, 
            BigDecimal.valueOf(25.45), EUR);

        when(priceRepository.getPriceByProductIdAndDateAndBrand(
            PRODUCT_ID, 
            LocalDateTime.parse("2020-06-14T16:00:00"), 
            BRAND_ID))
            .thenReturn(List.of(price0, price1));

        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(APPLICATION_DATE_PARAM, "2020-06-14T16:00:00")
                .param(BRAND_ID_PARAM, BRAND_ID.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
            .andExpect(jsonPath("$.brandId").value(BRAND_ID))
            .andExpect(jsonPath("$.priceListId").value(2))
            .andExpect(jsonPath("$.dateRange.startDate").value("2020-06-14T15:00:00"))
            .andExpect(jsonPath("$.dateRange.endDate").value("2020-06-14T18:30:00"))
            .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Should return price list 1 for June 14 at 9:00 PM")
    void shouldReturnPriceList1ForJune14At9PM() throws Exception {
        Price price = new Price(0L, 1, 
            LocalDateTime.parse("2020-06-14T00:00:00"), 
            LocalDateTime.parse("2020-12-31T23:59:59"), 
            1L, PRODUCT_ID, 1, 
            BigDecimal.valueOf(35.50), EUR);

        when(priceRepository.getPriceByProductIdAndDateAndBrand(
            PRODUCT_ID, 
            LocalDateTime.parse("2020-06-14T21:00:00"), 
            BRAND_ID))
            .thenReturn(List.of(price));

        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(APPLICATION_DATE_PARAM, "2020-06-14T21:00:00")
                .param(BRAND_ID_PARAM, BRAND_ID.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
            .andExpect(jsonPath("$.brandId").value(BRAND_ID))
            .andExpect(jsonPath("$.priceListId").value(1))
            .andExpect(jsonPath("$.dateRange.startDate").value("2020-06-14T00:00:00"))
            .andExpect(jsonPath("$.dateRange.endDate").value("2020-12-31T23:59:59"))
            .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Should return price list 3 for June 15 at 10:00 AM")
    void shouldReturnPriceList3ForJune15At10AM() throws Exception {
        Price price = new Price(2L, 1, 
            LocalDateTime.parse("2020-06-15T00:00:00"), 
            LocalDateTime.parse("2020-06-15T11:00:00"), 
            3L, PRODUCT_ID, 1, 
            BigDecimal.valueOf(30.50), EUR);

        when(priceRepository.getPriceByProductIdAndDateAndBrand(
            PRODUCT_ID, 
            LocalDateTime.parse("2020-06-15T10:00:00"), 
            BRAND_ID))
            .thenReturn(List.of(price));

        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(APPLICATION_DATE_PARAM, "2020-06-15T10:00:00")
                .param(BRAND_ID_PARAM, BRAND_ID.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
            .andExpect(jsonPath("$.brandId").value(BRAND_ID))
            .andExpect(jsonPath("$.priceListId").value(3))
            .andExpect(jsonPath("$.dateRange.startDate").value("2020-06-15T00:00:00"))
            .andExpect(jsonPath("$.dateRange.endDate").value("2020-06-15T11:00:00"))
            .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    @DisplayName("Should return price list 4 for June 16 at 9:00 PM")
    void shouldReturnPriceList4ForJune16At9PM() throws Exception {
        Price price0 = new Price(0L, 1, 
            LocalDateTime.parse("2020-06-14T00:00:00"), 
            LocalDateTime.parse("2020-12-31T23:59:59"), 
            1L, PRODUCT_ID, 0, 
            BigDecimal.valueOf(35.50), EUR);
        Price price1 = new Price(3L, 1, 
            LocalDateTime.parse("2020-06-15T16:00:00"), 
            LocalDateTime.parse("2020-12-31T23:59:59"), 
            4L, PRODUCT_ID, 1, 
            BigDecimal.valueOf(38.95), EUR);

        when(priceRepository.getPriceByProductIdAndDateAndBrand(
            PRODUCT_ID, 
            LocalDateTime.parse("2020-06-16T21:00:00"), 
            BRAND_ID))
            .thenReturn(List.of(price0, price1));

        String url = String.format(BASE_URL, PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(APPLICATION_DATE_PARAM, "2020-06-16T21:00:00")
                .param(BRAND_ID_PARAM, BRAND_ID.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
            .andExpect(jsonPath("$.brandId").value(BRAND_ID))
            .andExpect(jsonPath("$.priceListId").value(4))
            .andExpect(jsonPath("$.dateRange.startDate").value("2020-06-15T16:00:00"))
            .andExpect(jsonPath("$.dateRange.endDate").value("2020-12-31T23:59:59"))
            .andExpect(jsonPath("$.price").value(38.95));
    }
}