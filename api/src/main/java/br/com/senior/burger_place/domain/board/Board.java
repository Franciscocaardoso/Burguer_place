package br.com.senior.burger_place.domain.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "boards")
@Entity(name = "Board")
public class Board {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private int number;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private BoardLocation location;

    public Board(BoardRegisterData data) {
        this.number = data.number();
        this.capacity = data.capacity();
        this.location = data.boardLocation();
    }
}
