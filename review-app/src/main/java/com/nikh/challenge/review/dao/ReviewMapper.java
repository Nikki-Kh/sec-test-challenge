package com.nikh.challenge.review.dao;

import com.nikh.challenge.review.dto.ReviewBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Select("SELECT * from reviews where product_id = #{productId} LIMIT 1")
    @Results({
            @Result(column = "product_id", property = "productId"),
            @Result(column = "avg_score", property = "avgScore"),
            @Result(column = "review_num", property = "reviewNum")
    })
    ReviewBean getReviewById(@Param("productId") String productId);


    @Delete("DELETE from reviews where product_id = #{productId}")
    int deleteReviewById(@Param("productId") String productId);

    @Insert("INSERT into reviews (product_id, avg_score, review_num) " +
            "VALUES (#{review.productId}, #{review.avgScore}, #{review.reviewNum})")
    int insertReview(@Param("review") ReviewBean review);

    @Update("UPDATE reviews SET avg_score = #{review.avgScore}, review_num = #{review.reviewNum} " +
            "WHERE product_id = ")
    int updateReview(@Param("review") ReviewBean review);
}
