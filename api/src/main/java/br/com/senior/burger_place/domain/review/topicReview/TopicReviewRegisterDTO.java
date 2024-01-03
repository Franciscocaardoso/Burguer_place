package br.com.senior.burger_place.domain.review.topicReview;

import jakarta.validation.constraints.NotNull;

public record TopicReviewRegisterDTO(
        @NotNull
        Integer grade,
        @NotNull
        Category category
)  {
}
