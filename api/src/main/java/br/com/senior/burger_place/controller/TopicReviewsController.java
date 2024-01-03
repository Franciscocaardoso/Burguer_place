package br.com.senior.burger_place.controller;

import br.com.senior.burger_place.domain.review.topicReview.*;
import br.com.senior.burger_place.domain.review.topicReview.dto.ListingTopicReviewDTO;
import br.com.senior.burger_place.domain.review.topicReview.dto.TopicReviewRegisterDTO;
import br.com.senior.burger_place.domain.review.topicReview.dto.TopicReviewUpdateDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews/topics")
public class TopicReviewsController {

    @Autowired
    TopicReviewService service;

    @GetMapping()
    public ResponseEntity<Page<ListingTopicReviewDTO>> listAllTByCategory(
            @RequestParam(required = false) String category,
            Pageable pageable
    ) {
        try {
            Category categoryUpperCase = Category.valueOf(category.toUpperCase());
            Page<TopicReview> topicReviews = service.listTopicReviewByCategory(categoryUpperCase, pageable);
            return ResponseEntity.ok().body(topicReviews.map(ListingTopicReviewDTO::new));
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Não há avaliação para a categoria: " + category);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> update(
            @PathVariable
            Long id,
            @RequestBody
            TopicReviewUpdateDTO dto
    ) {
        TopicReviewRegisterDTO newTopicReview = service.updateTopicReview(id, dto);
        return ResponseEntity.ok().body(newTopicReview);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.deleteTopicReview(id);
        return ResponseEntity.noContent().build();
    }
}
