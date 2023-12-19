package br.com.senior.burger_place.domain.occupation.dto;

import br.com.senior.burger_place.domain.board.Board;
import br.com.senior.burger_place.domain.board.BoardLocation;

public record OccupationBoardDTO(
        Integer boardNumber,
        BoardLocation location
) {
    public OccupationBoardDTO(Board board) {
        this(
                board.getNumber(),
                board.getLocation()
        );
    }
}
