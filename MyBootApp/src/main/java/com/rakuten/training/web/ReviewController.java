package com.rakuten.training.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;
import com.rakuten.training.service.ProductService;
import com.rakuten.training.service.ReviewService;

@RestController
@CrossOrigin
public class ReviewController {
	  
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/products/{pid}/reviews")
	public ResponseEntity<List<Review>> getReviewsForAProduct(@PathVariable("pid") int productId){
		Product p = productService.findById(productId);
		if(p == null) {
			return new ResponseEntity<List<Review>>(HttpStatus.NOT_FOUND);
		}
		List<Review> reviews = reviewService.findByProductId(productId);
		return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
	}
	
	@PostMapping("/products/{pid}/reviews")
	public ResponseEntity<Review> addReviewToProduct(@PathVariable("pid") int productId,@RequestBody Review review){
		Product p = productService.findById(productId);
		if(p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Review added = reviewService.addReviewToProduct(productId, review);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/products/"+productId+"/reviews/"+added.getId()));
		return new ResponseEntity<Review>(added, headers, HttpStatus.CREATED);
	}
	
	
	
	

}






