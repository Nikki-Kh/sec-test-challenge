package com.nikh.challenge.service;

import org.springframework.http.RequestEntity;

public interface ProductInfoService {

    String getProductInfo(String productId, RequestEntity entity);
}
