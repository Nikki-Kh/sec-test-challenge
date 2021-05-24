package com.nikh.challenge.product.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nikh.challenge.product.dto.ReviewBean;
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

        Mono<ResponseEntity<String>> responseEntityMono =
                webClient.get().uri(liveInfoServiceUrl + productId)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(String.class);
        ResponseEntity<String> response = responseEntityMono.toFuture().get();
        if (!response.getStatusCode().equals(HttpStatus.OK)
                || !validateLiveResponse(response.getBody())) {
            //TODO: throw exception
        }
        return  JsonParser.parseString(response.getBody()).getAsJsonObject();

    }

    @SneakyThrows
    public ReviewBean getReviewInfo(String productId) {
        Mono<ResponseEntity<ReviewBean>> responseEntityMono =
                webClient.get().uri(reviewServiceUrl + productId)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(ReviewBean.class);
        ResponseEntity<ReviewBean> response = responseEntityMono.toFuture().get();
        if (!response.getStatusCode().equals(HttpStatus.OK)
                || response.getBody() != null) {
            //TODO: throw exception
        }
        return  response.getBody();
    }

    private boolean validateLiveResponse(String response) {
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        return jsonResponse.has("id");
    }
}