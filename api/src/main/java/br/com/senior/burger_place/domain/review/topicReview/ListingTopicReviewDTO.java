package br.com.senior.burger_place.domain.review.topicReview;

public record ListingTopicReviewDTO(
        Long id,
        int grade,
        Category category,
        Long occupationId
) {

    public ListingTopicReviewDTO(TopicReview topicReview) {
        this(topicReview.getId(), topicReview.getGrade(), topicReview.getCategory(), topicReview.getOccupation().getId());
    }
}
