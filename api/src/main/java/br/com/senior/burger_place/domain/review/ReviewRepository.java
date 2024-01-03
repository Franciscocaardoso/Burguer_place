package br.com.senior.burger_place.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(
            nativeQuery = true,
            value =
                    """
                    SELECT EXISTS (
                        SELECT 1
                        FROM occupations o
                        WHERE o.id = :id
                    );
                    """
    )
    boolean verifyOccupationExists(Long id);

    @Query(
            nativeQuery = true,
            value =
                    """
                    SELECT review_id, review_grade, review_comment, topic_review_id, topic_review_grade, topic_review_category, type
                    FROM (
                        SELECT r.id AS review_id, r.grade AS review_grade, r.comment AS review_comment, null AS topic_review_id, null AS topic_review_grade, null AS topic_review_category, 'Review' AS type
                        FROM reviews r
        
                        UNION ALL
        
                        SELECT tr.id AS review_id, tr.grade AS review_grade, null AS review_comment, tr.id AS topic_review_id, tr.grade AS topic_review_grade, tr.category AS topic_review_category, 'TopicReview' AS type
                        FROM topic_reviews tr
                    ) AS combined
                    ORDER BY review_grade DESC NULLS LAST
                    """
    )
    Page<Object[]> getAllReviewsAndTopicReviews(Pageable pageable);
}
