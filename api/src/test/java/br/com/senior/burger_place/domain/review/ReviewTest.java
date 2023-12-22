package br.com.senior.burger_place.domain.review;

import br.com.senior.burger_place.domain.occupation.Occupation;
import br.com.senior.burger_place.domain.review.dto.ReviewRegisterDTO;
import br.com.senior.burger_place.domain.review.dto.ReviewUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ReviewTest {

    @ParameterizedTest
    @CsvSource({"6", "10", "-1", "1000"})
    public void Review_whenGradeIsLessThan0OrGreaterThen5_shouldThrowException(int grade){

        Long id = 1l;
        ReviewRegisterDTO dto = new ReviewRegisterDTO(grade, "Comentário");

        assertThrows(NoSuchElementException.class, ()-> new Review(id, dto));
    }

    @Test
    public void Review_whenGradeIsValid_shouldSetAttributes(){

        Long occupationId = 1l;
        ReviewRegisterDTO dto = new ReviewRegisterDTO(4, "Comentário");
        Review review = new Review(occupationId, dto);

        assertEquals(dto.grade(), review.getGrade());
        assertEquals(dto.comment(), review.getComment());
        assertEquals(occupationId, review.getOccupation().getId());
    }

    @ParameterizedTest
    @CsvSource({"6", "10", "-1", "1000"})
    public void updateInformation_whenGradeIsLessThan0OrGreaterThen5_shouldThrowException(int grade){
        Review review = new Review();
        ReviewUpdateDTO dto = new ReviewUpdateDTO(grade, "Comentário");

        assertThrows(NoSuchElementException.class, ()-> review.updateInformation(dto));
    }

    @Test
    public void updateInformation_whenDataIsNotNull_shouldUpdateData(){
        Review review = new Review();
        ReviewUpdateDTO dto = new ReviewUpdateDTO(4, "Comentário");

        review.updateInformation(dto);

        assertEquals(review.getGrade(), dto.grade());
        assertEquals(review.getComment(), dto.comment());


    }

    @Test
    public void testAllArgsConstructor(){
        Long id = 1l;
        Integer grade = 4;
        String comment = "Comentário";
        Occupation occupation = mock(Occupation.class);

        Review review = new Review(id, grade, comment, occupation);

        assertEquals(id, review.getId());
        assertEquals(grade, review.getGrade());
        assertEquals(comment, review.getComment());
        assertEquals(occupation, review.getOccupation());
    }
    @Test
    public void testNoArgsConstructor(){

        Review review = new Review();

        assertNotNull(review);
        assertNull(review.getId());
        assertNull(review.getGrade());
        assertNull(review.getComment());
        assertNull(review.getOccupation());
    }

}