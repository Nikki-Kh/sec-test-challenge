package com.nikh.challenge.product.service;

import com.google.gson.JsonObject;
import com.nikh.challenge.product.client.ProductWebCLient;
import com.nikh.challenge.product.dto.ReviewBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService{

    private final ProductWebCLient webCLient;

    @Override
    public String getProductInfo(String productId) {
        JsonObject productInfo = webCLient.getLiveInfo(productId) ;
        ReviewBean reviewInfo = webCLient.getReviewInfo(productId);
        enrichResponseWithReviewParams(productInfo, reviewInfo);
        return productInfo.toString();
    }



    private void enrichResponseWithReviewParams(JsonObject jsonResponse, ReviewBean review) {
        jsonResponse.addProperty("avgScore", review.getAvgScore());
        jsonResponse.addProperty("reviewNum", review.getReviewNum());
    }
}
