package com.jmt.challengeindtx.domain.port.in;

import com.jmt.challengeindtx.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceServiceUseCase {
    Price getAppliedPriceByBrandAndDate(Long brandId, Long productId, LocalDateTime date);
}
