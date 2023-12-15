package br.com.senior.burger_place.controller;

import br.com.senior.burger_place.domain.board.Board;
import br.com.senior.burger_place.domain.board.BoardRegisterData;
import br.com.senior.burger_place.domain.board.BoardRepository;
import br.com.senior.burger_place.domain.board.BoardService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("boards")
public class BoardController {

    @Autowired
    private BoardService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> register(@RequestBody @Valid BoardRegisterData data){
        Board board = service.addBoard(data);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @GetMapping
    public ResponseEntity<Object> listBoard(@PageableDefault(sort = {"capacity"})Pageable pageable){
        return ResponseEntity.ok().build();
    }
}
