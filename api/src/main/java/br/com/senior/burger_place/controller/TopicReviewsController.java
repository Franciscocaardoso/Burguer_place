package br.com.senior.burger_place.controller;

import br.com.senior.burger_place.domain.review.topicReview.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("reviews/topics")
public class TopicReviewsController {

    @Autowired
    TopicReviewService service;

    @PostMapping("/{occupationId}")
    @Transactional
    public ResponseEntity register(
            @PathVariable
            Long occupationId,
            @RequestBody
            TopicReviewRegisterDTO dto,
            UriComponentsBuilder uriBuilder
    ) {
        TopicReview topicReview = service.addReview(occupationId, dto);
        var uri = uriBuilder.path("reviews/topics/{id}").buildAndExpand(topicReview.getId()).toUri();
        return ResponseEntity.created(uri).body(topicReview);
    }

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
            throw new EntityNotFoundException("Não avaliação para a categoria: " + category);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> update(
            @PathVariable
            Long id,
            @RequestBody
            TopicReviewUpdateDto dto
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
