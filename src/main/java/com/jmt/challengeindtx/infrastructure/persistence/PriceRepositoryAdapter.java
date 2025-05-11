package com.jmt.challengeindtx.infrastructure.persistence;

import com.jmt.challengeindtx.domain.model.Price;
import com.jmt.challengeindtx.domain.port.out.PriceRepository;
import com.jmt.challengeindtx.infrastructure.persistence.entity.PriceEntity;
import com.jmt.challengeindtx.infrastructure.persistence.mapper.PriceMapper;
import com.jmt.challengeindtx.infrastructure.persistence.repository.PriceH2Repository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Adapter class for PriceRepository.
 * This class is responsible for interacting with the data source to retrieve price information.
 * It implements the PriceRepository interface.
 */
@Component
public class PriceRepositoryAdapter implements PriceRepository {

    private final PriceH2Repository repository;
    private final PriceMapper priceMapper;

    public PriceRepositoryAdapter(PriceH2Repository repository, PriceMapper priceMapper) {
        this.repository = repository;
        this.priceMapper = priceMapper;
    }

    @Override
    public List<Price> getPriceByProductIdAndDateAndBrand(Long productId, LocalDateTime applicationDate, long brandId) {

        return repository.findByProductIdAndBrandIdAndDate(productId, brandId, applicationDate)
                .stream().map(priceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
