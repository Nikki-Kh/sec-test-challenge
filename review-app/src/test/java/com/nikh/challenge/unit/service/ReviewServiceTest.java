package com.nikh.challenge.unit.service;

import com.nikh.challenge.review.dao.ReviewMapper;

import com.nikh.challenge.review.dto.ReviewBean;
import com.nikh.challenge.review.service.ReviewService;
import com.nikh.challenge.review.service.ReviewServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;

import static  org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = {ReviewMapper.class, ReviewServiceImpl.class})
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @MockBean
    ReviewMapper reviewMapper;

    @Before
    public void init() {
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setReviewNum(100);
        reviewBean.setAvgScore(5.5f);
        reviewBean.setProductId("BB2406");
        when(reviewMapper.getReviewById("BB2406")).thenReturn(reviewBean);
        when(reviewMapper.getReviewById(not(eq("BB2406")))).thenReturn(null);
        when(reviewMapper.deleteReviewById("BB2406")).thenReturn(1);
        when(reviewMapper.deleteReviewById(not(eq("BB2406")))).thenReturn(0);
        when(reviewMapper.insertReview(reviewBean)).thenReturn(1);
        when(reviewMapper.insertReview(not(eq(reviewBean)))).thenReturn(0);
        when(reviewMapper.updateReview(reviewBean)).thenReturn(1);
        when(reviewMapper.updateReview(not(eq(reviewBean)))).thenReturn(0);
    }

    @Test
    public void testGet() {
        ReviewBean reviewBean1 = reviewMapper.getReviewById("BB2406");
        assertNotNull(reviewBean1);
        assertEquals(100, reviewBean1.getReviewNum().intValue());
        assertEquals(5.5f, reviewBean1.getAvgScore().floatValue());
        ReviewBean reviewBean2 = reviewMapper.getReviewById("BB2407");
        assertNull(reviewBean2);
    }

    @Test
    public void testUpdate() {
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setReviewNum(100);
        reviewBean.setAvgScore(5.5f);
        reviewBean.setProductId("BB2406");

        ReviewBean reviewBean2 = new ReviewBean();
        reviewBean2.setReviewNum(100);
        reviewBean2.setAvgScore(5.5f);
        reviewBean2.setProductId("BB2407");

        assertDoesNotThrow(() -> reviewService.updateReview(reviewBean));
        assertThrows(RuntimeException.class, () -> reviewService.updateReview(reviewBean2));
    }

    @Test
    public void testCreate() {
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setReviewNum(100);
        reviewBean.setAvgScore(5.5f);
        reviewBean.setProductId("BB2406");

        ReviewBean reviewBean2 = new ReviewBean();
        reviewBean2.setReviewNum(100);
        reviewBean2.setAvgScore(5.5f);
        reviewBean2.setProductId("BB2407");

        assertDoesNotThrow(() -> reviewService.postReview(reviewBean));
        assertThrows(RuntimeException.class, () -> reviewService.postReview(reviewBean2));

    }

    @Test
    public void testDelete() {
        assertDoesNotThrow(() -> reviewService.deleteReview("BB2406"));
        assertThrows(RuntimeException.class, () -> reviewService.deleteReview("BB2407"));
    }
}
