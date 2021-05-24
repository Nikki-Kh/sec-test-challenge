package com.nikh.challenge.unit.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nikh.challenge.product.client.ProductWebCLient;
import com.nikh.challenge.product.dto.ReviewBean;
import com.nikh.challenge.product.error.exception.ProductAbsentException;
import com.nikh.challenge.product.service.ProductInfoService;
import com.nikh.challenge.product.service.ProductInfoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.*;
import static  org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductWebCLient.class, ProductInfoServiceImpl.class})
public class ProductInfoServiceImplTest {

    @MockBean
    ProductWebCLient webCLient;

    @Autowired
    private ProductInfoService productInfoService;


    @Before
    public void init() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", "BB2406");
        obj.addProperty("addInfo", "Some additional info to fill the object");

        ReviewBean review = new ReviewBean();
        review.setProductId("BB2406");
        review.setAvgScore(5.5f);
        review.setReviewNum(100);

        when(webCLient.getLiveInfo("BB2406")).thenReturn(obj);
        when(webCLient.getReviewInfo("BB2406")).thenReturn(review);

    }

    @Test
    public void testGetProductInfo() {
        String result = productInfoService.getProductInfo("BB2406");
        JsonObject obj = JsonParser.parseString(result).getAsJsonObject();
        assertAll(List.of("id", "avgScore", "reviewNum").stream().map(it -> (() -> assertTrue(obj.has(it)))));
        assertEquals("BB2406", obj.get("id").getAsString());
        assertEquals(5.5f, obj.get("avgScore").getAsFloat());
        assertEquals(100, obj.get("reviewNum").getAsInt());
    }

    @Test
    public void testLiveInfoFailWithException() {
        when(webCLient.getLiveInfo("BB2407")).thenThrow(new ProductAbsentException("BB2407"));
        when(webCLient.getReviewInfo("BB2407")).thenReturn(null);

        assertThrows(ProductAbsentException.class, () -> productInfoService.getProductInfo("BB2407"));
    }

    @Test
    public void testReviewInfoFailWithException() {
        when(webCLient.getReviewInfo("BB2407")).thenThrow(new ProductAbsentException("BB2407"));
        when(webCLient.getLiveInfo("BB2407")).thenReturn(null);

        assertThrows(ProductAbsentException.class, () -> productInfoService.getProductInfo("BB2407"));
    }
}
