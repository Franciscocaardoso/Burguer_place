package br.com.senior.delivery.controller;

import br.com.senior.delivery.domain.review.Review;
import br.com.senior.delivery.domain.review.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    ReviewRepository repository;

//    @PostMapping
//    @Transactional
//    public ResponseEntity register(){
//        Review review; // CONTINUAR
//        return ResponseEntity.ok();
//    }
}
