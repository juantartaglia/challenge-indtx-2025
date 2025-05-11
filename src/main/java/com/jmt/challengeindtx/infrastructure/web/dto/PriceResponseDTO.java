package com.jmt.challengeindtx.infrastructure.web.dto;

import java.math.BigDecimal;

public record PriceResponseDTO(Long productId, Long brandId, long priceListId, PriceDateRangeDTO dateRange, BigDecimal price) {
}
