package com.jmt.challengeindtx.infrastructure.persistence;

import com.jmt.challengeindtx.domain.model.Price;
import com.jmt.challengeindtx.domain.port.out.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Adapter class for PriceRepository.
 * This class is responsible for interacting with the data source to retrieve price information.
 * It implements the PriceRepository interface.
 */
@Component
public class PriceRepositoryAdapter implements PriceRepository {

    @Override
    public List<Price> getPriceByProductIdAndDateAndBrand(Long productId, LocalDateTime applicationDate, long brandId) {
        return List.of();
    }
}
