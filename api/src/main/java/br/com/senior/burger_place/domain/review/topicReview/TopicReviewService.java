package br.com.senior.burger_place.domain.review.topicReview;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicReviewService {

    @Autowired
    TopicReviewRepository repository;

    public TopicReview addReview(Long occupationId, TopicReviewRegisterDTO dto) {
        if (!repository.verifyOccupationExists(occupationId)){
            throw new EntityNotFoundException("Não existe uma ocupação com esse ID");
        }
        return repository.save(new TopicReview(occupationId, dto));
    }

    public void deleteTopicReview(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Avaliação não existe");
        }
        repository.deleteById(id);
    }

    public TopicReviewRegisterDTO updateTopicReview(Long id, TopicReviewUpdateDto dto) {
        Optional<TopicReview> optionalTopicReview = repository.findById(id);
        if (optionalTopicReview.isEmpty()) {
            throw new EntityNotFoundException("Avaliação não existe");
        }
        TopicReview topicReview = optionalTopicReview.get();
        topicReview.updateInformation(dto);
        TopicReviewRegisterDTO responseData = new TopicReviewRegisterDTO(topicReview.getGrade(), topicReview.getCategory());
        return responseData;
    }

    public Page<TopicReview> listTopicReviewByCategory(Category category, Pageable pageable) {
        Page<TopicReview> topicReviews = repository.findByCategory(category, pageable);
        return topicReviews;
    }
}
