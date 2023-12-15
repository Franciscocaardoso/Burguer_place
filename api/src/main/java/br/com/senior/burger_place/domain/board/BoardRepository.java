package br.com.senior.burger_place.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByNumber(int number);

    boolean existsByIdAndActiveTrue(Long id);

    @Query("""
            SELECT board
            FROM Board
            WHERE id = ?1
            AND id NOT IN (
                SELECT occupation.board.id
                FROM Occupation occupation
                WHERE occupation endOccupation = NULL
            )
            """)
    boolean isBoardOccupied(Long id);

    Board getReferenceByIdAndActiveTrue(Long id);
}

