package br.com.senior.burger_place.controller;

import br.com.senior.burger_place.domain.review.ReviewRepository;
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
