package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.DTO.Request.ReviewRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
   ReviewResponseDTO addReview(Integer userId, Integer productId, ReviewRequestDTO dto);
   List<ReviewResponseDTO> getReviewByProduct(Integer productId);
   Page<ReviewResponseDTO> listReivew(Pageable pageable);
   String deleteReivew(Integer reviewId);
}
