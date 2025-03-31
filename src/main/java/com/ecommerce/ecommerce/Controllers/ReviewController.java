package com.ecommerce.ecommerce.Controllers;

import com.ecommerce.ecommerce.DTO.Request.ReviewRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.ReviewResponseDTO;
import com.ecommerce.ecommerce.Services.ServiceImpl.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reviews", description = "Reviews rest endpoints")
@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/user/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestParam Integer userId, @RequestParam Integer productId, @RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.status(201).body(reviewService.addReview(userId, productId, dto));
    }

    @GetMapping("/product/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewByProduct(@PathVariable Integer productId) {
        return ResponseEntity.status(200).body(reviewService.getReviewByProduct(productId));
    }

    @GetMapping("/admin/reviews")
    public ResponseEntity<Page<ReviewResponseDTO>> listReview(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ResponseEntity.status(200).body(reviewService.listReivew(pageable));
    }

    @DeleteMapping("/admin/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer id){
        return ResponseEntity.status(200).body(reviewService.deleteReivew(id));
    }
}
