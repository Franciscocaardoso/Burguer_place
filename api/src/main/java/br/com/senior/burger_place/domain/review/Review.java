package br.com.senior.burger_place.domain.review;

import br.com.senior.burger_place.domain.occupation.Occupation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "reviews")
@Entity(name = "Review")
public class Review {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private int grade;
    private String comment;
    private int qtdGrades; // ESSE ATRIBUTO DEVE SER AUTOINCREMENTADO APÓS CADA AVALIAÇÃO FEITA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Occupation occupation;
}
