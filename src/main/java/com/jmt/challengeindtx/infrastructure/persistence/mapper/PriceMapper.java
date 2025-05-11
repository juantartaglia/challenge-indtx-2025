package com.jmt.challengeindtx.infrastructure.persistence.mapper;

import com.jmt.challengeindtx.domain.model.Price;
import com.jmt.challengeindtx.infrastructure.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PriceMapper {

    @Mapping(source = "priceListId", target = "priceListId")
    @Mapping(source = "curr", target = "curr")
    Price toDomain(PriceEntity jpaPrice);

    @Mapping(source = "priceListId", target = "priceListId")
    @Mapping(source = "curr", target = "curr")
    PriceEntity toEntity(Price price);

}