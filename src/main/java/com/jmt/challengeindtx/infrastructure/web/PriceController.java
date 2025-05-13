package com.jmt.challengeindtx.infrastructure.web;

import com.jmt.challengeindtx.domain.model.Price;
import com.jmt.challengeindtx.domain.port.in.PriceServiceUseCase;
import com.jmt.challengeindtx.infrastructure.web.dto.PriceResponseDTO;
import com.jmt.challengeindtx.infrastructure.web.mapper.PriceResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/product")
@Validated
@Tag(name = "Product Price Calculator", description = "API para el cálculo de precios")
public class PriceController {

    private final PriceServiceUseCase getPriceUseCase;
    private final PriceResponseMapper priceResponseMapper;

    public PriceController(PriceServiceUseCase getPriceUseCase, PriceResponseMapper priceResponseMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceResponseMapper = priceResponseMapper;
    }

    @GetMapping("/{product_id}/price")
    @Operation(summary = "Obtener precio aplicable", description = "Obtiene el precio aplicable para un producto en una fecha específica")
    public ResponseEntity<PriceResponseDTO> getPrice(
            @Parameter(description = "ID del producto") @PathVariable("product_id") Long productId,
            @Parameter(description = "ID de la marca") @RequestParam @NotNull Long brandId,
            @Parameter(description = "Fecha de aplicación") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {


        Price price = getPriceUseCase.getAppliedPriceByBrandAndDate(brandId, productId, applicationDate);
        return ResponseEntity.ok(priceResponseMapper.toResponse(price));
    }
}
