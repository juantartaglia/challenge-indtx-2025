package com.jmt.challengeindtx.application.service;

import com.jmt.challengeindtx.domain.exception.ResourceNotFoundException;
import com.jmt.challengeindtx.domain.model.Price;
import com.jmt.challengeindtx.domain.port.in.PriceServiceUseCase;
import com.jmt.challengeindtx.domain.port.out.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Service class to handle price-related operations.
 * Implements the PriceServiceUseCase interface.
 */
@Service
public class PriceService implements PriceServiceUseCase {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getAppliedPriceByBrandAndDate(Long brandId, Long productId, LocalDateTime date) {
        return priceRepository.getPriceByProductIdAndDateAndBrand(productId, date, brandId)
                .stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new ResourceNotFoundException("No price found for the given criteria"));
    }
}
