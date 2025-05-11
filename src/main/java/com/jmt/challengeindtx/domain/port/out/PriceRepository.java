package com.jmt.challengeindtx.domain.port.out;

import com.jmt.challengeindtx.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> getPriceByProductIdAndDateAndBrand(Long productId, LocalDateTime applicationDate, long brandId);
}