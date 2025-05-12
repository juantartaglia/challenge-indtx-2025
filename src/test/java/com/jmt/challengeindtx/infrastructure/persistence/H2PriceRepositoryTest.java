package com.jmt.challengeindtx.infrastructure.persistence;

import com.jmt.challengeindtx.infrastructure.persistence.entity.PriceEntity;
import com.jmt.challengeindtx.infrastructure.persistence.repository.PriceH2Repository;
import com.jmt.challengeindtx.infrastructure.shared.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class H2PriceRepositoryTest {

    @Autowired
    PriceH2Repository underTest;

    @Test
    void getPriceByProductIdAndDateAndBrand() {

        //given
        PriceEntity price0 = new PriceEntity(1, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 1L, 35455L, 0, BigDecimal.valueOf(35.50), Currency.EUR);
        underTest.save(price0);
        PriceEntity price1 = new PriceEntity(1, LocalDateTime.parse("2020-06-14T15:00:00"), LocalDateTime.parse("2020-06-14T18:30:00"), 2L, 35455L, 1, BigDecimal.valueOf(25.45), Currency.EUR);
        underTest.save(price1);
        PriceEntity price2 = new PriceEntity(1, LocalDateTime.parse("2020-06-15T00:00:00"), LocalDateTime.parse("2020-06-15T11:00:00"), 3L, 35455L, 1, BigDecimal.valueOf(30.50), Currency.EUR);
        underTest.save(price2);
        PriceEntity price3 = new PriceEntity(1, LocalDateTime.parse("2020-06-15T16:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 4L, 35455L, 1, BigDecimal.valueOf(38.95), Currency.EUR);
        underTest.save(price3);

        //when
        List<PriceEntity> expect = underTest.findByProductIdAndBrandIdAndDate(35455L, 1L, LocalDateTime.parse("2020-06-14T16:00:00"));

        //then
        assertThat(expect).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(price0, price1);
    }
}
