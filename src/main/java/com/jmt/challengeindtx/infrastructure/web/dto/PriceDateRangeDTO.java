package com.jmt.challengeindtx.infrastructure.web.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record PriceDateRangeDTO(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
}
