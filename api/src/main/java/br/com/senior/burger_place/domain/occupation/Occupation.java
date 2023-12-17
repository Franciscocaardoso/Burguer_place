package br.com.senior.burger_place.domain.occupation;

import br.com.senior.burger_place.domain.board.Board;
import br.com.senior.burger_place.domain.occupation.dto.FinishOccupationDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "occupations")
@Entity(name = "Occupation")
@ToString
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime beginOccupation;
    private LocalDateTime endOccupation;
    private Integer peopleCount;
    @Enumerated(EnumType.STRING)
    private PaymentForm paymentForm;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "occupation")
    private List<OrderItem> orderItems;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    private boolean active;

    public Occupation(LocalDateTime beginOccupation, Integer peopleCount, Board board) {
        this.beginOccupation = beginOccupation;
        this.peopleCount = peopleCount;
        this.board = board;
        this.active = true;
    }

    public void inactivate() {
        this.active = false;
    }

    public void finish(FinishOccupationDTO occupationDTO) {
        this.endOccupation = occupationDTO.endOccupation();
        this.paymentForm = occupationDTO.paymentForm();
    }
}
