package com.nikh.challenge.dao;

import com.nikh.challenge.dto.ReviewBean;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ReviewMapper {

    @Select("SELECT * from reviews were product_id = #{productId}")
    ReviewBean getReviewById(@Param("productId") String productId);

    @Delete("DELETE from reviews were product_id = #{productId}")
    int deleteReviewById(@Param("productId") String productId);

    @Insert("INSERT into reviews (product_id, avg_score, review_num) " +
            "VALUES (#{review.productId}, #{review.avgScore}, #{review.reviewNum})")
    int insertReview(@Param("review") ReviewBean review);

    @Update("UPDATE reviews SET avg_score = #{review.avgScore}, review_num = #{review.reviewNum} " +
            "WHERE product_id = ")
    int updateReview(@Param("review") ReviewBean review);
}
