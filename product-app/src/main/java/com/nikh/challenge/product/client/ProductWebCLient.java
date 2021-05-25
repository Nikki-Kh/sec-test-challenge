package com.nikh.challenge.product.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nikh.challenge.product.dto.ReviewBean;
import com.nikh.challenge.product.error.exception.ApiException;
import com.nikh.challenge.product.error.exception.ExternalCallException;
import com.nikh.challenge.product.error.exception.ProductAbsentException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductWebCLient {

    private final WebClient webClient;

    @Value("${rest.endpoint.review}")
    private String reviewServiceUrl;

    @Value("${rest.endpoint.live}")
    private String liveInfoServiceUrl;

    @SneakyThrows
    public JsonObject getLiveInfo(String productId) {
        try {
        Mono<ResponseEntity<String>> responseEntityMono =
                webClient.get().uri(liveInfoServiceUrl + productId)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(String.class);
        ResponseEntity<String> response = responseEntityMono.toFuture().get();
        validateLiveResponse(productId, response);
        return  JsonParser.parseString(response.getBody()).getAsJsonObject();
        }
        catch (ApiException e) {
            throw e;
        }
        catch (Exception e) {
            if (e.getMessage().contains("404")) {
                throw new ProductAbsentException(productId);
            } else {
                throw new ExternalCallException(productId);
            }
        }
    }

    @SneakyThrows
    public ReviewBean getReviewInfo(String productId) {
        try {
            Mono<ResponseEntity<ReviewBean>> responseEntityMono =
                    webClient.get().uri(reviewServiceUrl + productId)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .toEntity(ReviewBean.class);
            ResponseEntity<ReviewBean> response = responseEntityMono.toFuture().get();
            validateResponse(productId, response);
            return response.getBody();
        }
        catch (ApiException e) {
            throw e;
        }
        catch (Exception e) {
            if (e.getMessage().contains("404")) {
                throw new ProductAbsentException(productId);
            } else {
                throw new ExternalCallException(productId);
            }
        }
    }

    private void validateResponse(String productId, ResponseEntity response) {
        if (response == null
                || !response.getStatusCode().equals(HttpStatus.OK)
                && !response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new ExternalCallException(productId);
        }
        if (response.getBody() == null || response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new ProductAbsentException(productId);
        }
    }

    private void validateLiveResponse(String productId, ResponseEntity<String> response) {
        validateResponse(productId, response);
        JsonObject jsonResponse = JsonParser.parseString(response.getBody()).getAsJsonObject();
        if (!jsonResponse.has("id")) {
            throw new ProductAbsentException(productId);
        }
    }
}
