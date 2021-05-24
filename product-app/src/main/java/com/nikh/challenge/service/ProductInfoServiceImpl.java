package com.nikh.challenge.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nikh.challenge.dto.ReviewBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService{

    private final RestTemplate restClient;

    @Value("${rest.endpoint.review}")
    private String reviewServiceUrl;

    @Value("${rest.endpoint.live}")
    private String liveInfoServiceUrl;

    @Override
    public String getProductInfo(String productId, RequestEntity requestEntity) {
        URI liveURI = URI.create(liveInfoServiceUrl + productId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setHost(requestEntity.getHeaders().getHost());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try{
            ResponseEntity<String> response = restClient.exchange(liveURI, HttpMethod.GET, entity, String.class);
            if (HttpStatus.OK.equals(response.getStatusCode()) && validateLiveResponse(response.getBody())) {
                URI reviewURI = URI.create(reviewServiceUrl + productId);
                ReviewBean review = restClient.getForObject(reviewURI, ReviewBean.class);
                JsonObject jsonResponse = JsonParser.parseString(response.getBody()).getAsJsonObject();
                enrichResponseWithReviewParams(jsonResponse, review);
                return jsonResponse.getAsString();
            }
        }
        catch (Exception e) {
            log.error("failed to retrieve info", e);
        }
        return null;
    }



    private boolean validateLiveResponse(String response) {
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        return jsonResponse.has("id");
    }

    private void enrichResponseWithReviewParams(JsonObject jsonResponse, ReviewBean review) {
        jsonResponse.addProperty("avgScore", review.getAvgScore());
        jsonResponse.addProperty("reviewNum", review.getReviewNum());
    }
}
