package br.com.senior.burger_place.domain.review.topicReview;

import br.com.senior.burger_place.domain.occupation.Occupation;
import br.com.senior.burger_place.domain.review.Review;
import br.com.senior.burger_place.domain.review.dto.ReviewUpdateDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.NoSuchElementException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "topic_reviews")
@Entity(name = "TopicReview")
public class TopicReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer grade;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    @JsonIgnoreProperties({"beginOccupation", "endOccupation", "peopleCount", "paymentForm", "orderItems", "board", "customers", "active"})
    private Occupation occupation;

    public TopicReview(Long occupationId, TopicReviewRegisterDTO dto) {
        if (dto.grade() < 1 || dto.grade() > 5) {
            throw new NoSuchElementException("A nota deve ser entre 1 e 5");
        }
        if (dto.category() != null){
            this.category = dto.category();
        }
        this.grade = dto.grade();
        this.occupation = new Occupation(occupationId);
    }

    public void updateInformation(TopicReviewUpdateDto data) {
        if (data.grade() != null && (data.grade() < 1 || data.grade() > 5)){
            throw new NoSuchElementException("A nota deve ser entre 1 e 5");
        } else {
            this.grade = data.grade();
        }
    }
}
