package com.jmt.challengeindtx.infrastructure.persistence.repository;

import com.jmt.challengeindtx.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceH2Repository extends JpaRepository<PriceEntity, Long> {

    @Query("""
            SELECT pe FROM PriceEntity pe
            WHERE
            pe.productId = :productId AND
            pe.brandId = :brandId AND
            (pe.startDate <= :date AND :date <=pe.endDate)
    """)
    List<PriceEntity> findByProductIdAndBrandIdAndDate(@Param("productId") Long productId,
                                                       @Param("brandId") Long brandId,
                                                       @Param("date") LocalDateTime date);

}
