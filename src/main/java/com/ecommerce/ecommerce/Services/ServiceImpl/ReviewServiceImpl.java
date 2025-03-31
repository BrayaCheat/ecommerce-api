package com.ecommerce.ecommerce.Services.ServiceImpl;

import com.ecommerce.ecommerce.DTO.Mapper.ReviewMapper;
import com.ecommerce.ecommerce.DTO.Request.ReviewRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.ReviewResponseDTO;
import com.ecommerce.ecommerce.Models.Product;
import com.ecommerce.ecommerce.Models.Review;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repositories.ProductRepository;
import com.ecommerce.ecommerce.Repositories.ReviewRepository;
import com.ecommerce.ecommerce.Repositories.UserRepository;
import com.ecommerce.ecommerce.Services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, UserRepository userRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ReviewResponseDTO addReview(Integer userId, Integer productId, ReviewRequestDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with id: " + userId + " not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product with id: " + productId + " not found"));
        Review review = Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .user(user)
                .product(product)
                .build();
        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getReviewByProduct(Integer productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        if(reviews.isEmpty()){
            throw new RuntimeException("No review found on product id " + productId);
        }
        return reviews.stream().map(reviewMapper::toDTO).toList();
    }

    @Override
    public Page<ReviewResponseDTO> listReivew(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toDTO);
    }

    @Override
    public String deleteReivew(Integer reviewId) {
        reviewRepository.deleteById(reviewId);
        return "Review deleted.";
    }
}
