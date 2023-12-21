package br.com.senior.burger_place.controller;

import br.com.senior.burger_place.domain.board.*;
import br.com.senior.burger_place.domain.board.dto.BoardRegisterDTO;
import br.com.senior.burger_place.domain.board.dto.BoardUpdateDTO;
import br.com.senior.burger_place.domain.board.dto.ListingBoardDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Object> register(@RequestBody @Valid BoardRegisterDTO dto){
        Board board = service.addBoard(dto);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateBoard(
            @PathVariable
            Long id,
            @RequestBody
            @Valid
            BoardUpdateDTO dto
    ){
        service.updateBoard(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<Board>> listAllBoards(@PageableDefault(size = 10, sort = {"location"}) Pageable pageable){
        Page<Board> boards = service.listAllBoards(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> listBoardById(@PathVariable Long id){
        Board board = service.verifyOccupiedBoard(id);
        return ResponseEntity.ok(new ListingBoardDTO(board));
    }

    @GetMapping("/locale")
    public ResponseEntity<Page<ListingBoardDTO>> listBoardByLocation(
            @RequestParam
            (required = false) BoardLocation location,
            @PageableDefault(size = 10)
            Pageable pageable) {
        Page<ListingBoardDTO> boards;

        if (location == null) {
            throw new EntityNotFoundException("Localização não pode ser nula");
        }
        boards = service.listBoardsByLocation(location, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteBoard(@PathVariable Long id){
        Board board = service.listBoardsById(id);
        board.inactivate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
