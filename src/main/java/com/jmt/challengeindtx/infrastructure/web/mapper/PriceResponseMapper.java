package com.jmt.challengeindtx.infrastructure.web.mapper;

import com.jmt.challengeindtx.domain.model.Price;
import com.jmt.challengeindtx.infrastructure.web.dto.PriceDateRangeDTO;
import com.jmt.challengeindtx.infrastructure.web.dto.PriceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PriceResponseMapper {


    @Mapping(target = "dateRange", expression = "java(mapToDateRange(price))")
    PriceResponseDTO toResponse(Price price);

    default PriceDateRangeDTO mapToDateRange(Price price) {
        if (price == null) {
            return null;
        }
        return new PriceDateRangeDTO(price.getStartDate(), price.getEndDate());
    }
}
