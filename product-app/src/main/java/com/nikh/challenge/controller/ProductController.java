package com.nikh.challenge.controller;

import com.nikh.challenge.service.ProductInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductInfoService productInfoService;

    @GetMapping("/product/{productId}")
    public ResponseEntity getProductInfo(@PathVariable("productId") String productId,
                                         RequestEntity requestEntity) {
        String response = productInfoService.getProductInfo(productId, requestEntity);
        return ResponseEntity.ok(response);
    }

}
