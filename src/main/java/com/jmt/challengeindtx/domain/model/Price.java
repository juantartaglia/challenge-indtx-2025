package com.jmt.challengeindtx.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Price {

    private long id;
    private long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long priceListId;
    private Long productId;
    private Integer priority;
    private BigDecimal price;
    private String curr;

    public Price(long id, long brandId, LocalDateTime startDate, LocalDateTime endDate, Long priceListId, Long productId, Integer priority, BigDecimal price, String curr) {
        this.id = id;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceListId = priceListId;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.curr = curr;
    }
    public Price() { }

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

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }
}
