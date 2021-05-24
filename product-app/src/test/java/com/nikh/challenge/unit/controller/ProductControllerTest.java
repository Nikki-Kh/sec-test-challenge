package com.nikh.challenge.unit.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nikh.challenge.product.controller.ProductController;
import com.nikh.challenge.product.service.ProductInfoService;
import com.nikh.challenge.product.service.ProductInfoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static  org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductController.class, ProductInfoServiceImpl.class})
public class ProductControllerTest {

    @Autowired
    private ProductController controller;

    @MockBean
    ProductInfoService productInfoService;

    @Before
    public void init() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", "BB2406");
        obj.addProperty("avgScore", 5.75);
        obj.addProperty("reviewNum", 100);
        when(productInfoService.getProductInfo(eq("BB2406"))).thenReturn(obj.toString());
    }

    @Test
    public void testGetProductInfo() {
        String productId = "BB2406";
        ResponseEntity result = controller.getProductInfo(productId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        JsonObject obj = JsonParser.parseString(result.getBody().toString()).getAsJsonObject();
        assertAll(List.of("id", "avgScore", "reviewNum").stream().map(it -> (() -> assertTrue(obj.has(it)))));
    }
}
