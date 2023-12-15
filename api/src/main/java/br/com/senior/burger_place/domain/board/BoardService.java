package br.com.senior.burger_place.domain.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

public class BoardService {

    @Autowired
    BoardRepository repository;

    public Board addBoard(BoardRegisterData data) {
        if (repository.existsByNumber(data.number())){
            throw new DuplicateKeyException("Já existe uma mesa cadastrada com esse número");
        }
        return repository.save(new Board(data));
    }
}
