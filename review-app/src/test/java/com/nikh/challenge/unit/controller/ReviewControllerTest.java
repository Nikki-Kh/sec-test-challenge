package com.nikh.challenge.unit.controller;


import com.nikh.challenge.review.controller.ReviewController;
import com.nikh.challenge.review.dto.ReviewBean;
import com.nikh.challenge.review.dto.ReviewRequest;
import com.nikh.challenge.review.service.AuthService;
import com.nikh.challenge.review.service.ReviewService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;
import static  org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ReviewController.class, ReviewService.class})
public class ReviewControllerTest {


    @Autowired
    ReviewController reviewController;

    @MockBean
    ReviewService reviewService;

    @MockBean
    AuthService authService;

    @Before
    public void init() {
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setReviewNum(100);
        reviewBean.setAvgScore(5.5f);
        reviewBean.setProductId("BB2406");

        when(authService.authorize(anyString())).thenReturn(true);

        when(reviewService.getReview("BB2406")).thenReturn(reviewBean);
        when(reviewService.getReview(not(eq("BB2406")))).thenReturn(null);

        doNothing().when(reviewService).updateReview(reviewBean);
        doThrow(new RuntimeException("text")).when(reviewService).updateReview(not(eq(reviewBean)));

        doNothing().when(reviewService).postReview(reviewBean);
        doThrow(new RuntimeException("text")).when(reviewService).postReview(not(eq(reviewBean)));

        doNothing().when(reviewService).deleteReview("BB2406");
        doThrow(new RuntimeException("text")).when(reviewService).deleteReview(not(eq("BB2406")));
    }

    @Test
    public void testGetReview() {
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setReviewNum(100);
        reviewBean.setAvgScore(5.5f);
        reviewBean.setProductId("BB2406");

        ResponseEntity<ReviewBean> response = reviewController.getReview("BB2406");
        assertEquals(reviewBean, response.getBody());

        ResponseEntity<ReviewBean> response2 = reviewController.getReview("BB2407");
        assertNull(response2.getBody());
    }

    @Test
    public void testInsertReview() {
        ReviewRequest request = new ReviewRequest();
        request.setReviewNum(100);
        request.setAvgScore(5.5f);

        ReviewRequest request2 = new ReviewRequest();
        request2.setReviewNum(100);
        request2.setAvgScore(5.5f);

        assertDoesNotThrow(() -> reviewController.createReview("BB2406", "test", request));
        assertThrows(RuntimeException.class, () -> reviewController.createReview("BB2407", "test", request));
    }

    @Test
    public void testDeleteReview() {
        assertDoesNotThrow(() -> reviewController.deleteReview("BB2406", "test"));
        assertThrows(RuntimeException.class, () -> reviewController.deleteReview("BB2407", "test"));
    }

    @Test
    public void testUpdateReview() {
        ReviewRequest request = new ReviewRequest();
        request.setReviewNum(100);
        request.setAvgScore(5.5f);

        ReviewRequest request2 = new ReviewRequest();
        request2.setReviewNum(100);
        request2.setAvgScore(5.5f);

        assertDoesNotThrow(() -> reviewController.updateReview("BB2406", "test", request));
        assertThrows(RuntimeException.class, () -> reviewController.updateReview("BB2407", "test", request));
    }
}
