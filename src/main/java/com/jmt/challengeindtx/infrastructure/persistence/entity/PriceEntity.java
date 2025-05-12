package com.jmt.challengeindtx.infrastructure.persistence.entity;

import com.jmt.challengeindtx.infrastructure.shared.Currency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES", indexes = {
        @Index(name = "idx_prices_brand_product", columnList = "BRAND_ID, PRODUCT_ID"),
        @Index(name = "idx_prices_dates", columnList = "START_DATE, END_DATE")
})
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "BRAND_ID")
    private long brandId;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @NotNull
    @Column(name = "PRICE_LIST")
    private Long priceListId;

    @NotNull
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @NotNull
    @Column(name = "PRIORITY")
    private Integer priority;

    @NotNull
    @Positive
    @Column(name = "PRICE")
    private BigDecimal price;

    @NotNull
    @Column(name = "CURR", length = 3)
    @Enumerated(EnumType.STRING)
    private Currency curr;


    public PriceEntity() {
    }

    public PriceEntity(long brandId, LocalDateTime startDate, LocalDateTime endDate, Long priceListId, Long productId, Integer priority, BigDecimal price, Currency curr) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceListId = priceListId;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.curr = curr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(Long priceListId) {
        this.priceListId = priceListId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurr() {
        return curr;
    }

    public void setCurr(Currency curr) {
        this.curr = curr;
    }
}
