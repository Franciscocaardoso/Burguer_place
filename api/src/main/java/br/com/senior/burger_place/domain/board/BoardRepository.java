package br.com.senior.burger_place.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByNumber(int number);

    boolean existsByIdAndActiveTrue(Long id);

    @Query(
            nativeQuery = true,
            value =
                    """
                    SELECT EXISTS (
                        SELECT 1
                        FROM boards b
                        WHERE b.id = :id
                        AND b.id IN (
                            SELECT o.board_id
                            FROM occupations o
                            WHERE o.end_occupation IS NULL
                            AND o.active IS TRUE
                        )
                        AND b.active IS TRUE
                    );
                    """)
    boolean isBoardOccupied(Long id);

    Board getReferenceByIdAndActiveTrue(Long id);
}

