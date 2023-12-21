package br.com.senior.burger_place.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlerError {

    @ExceptionHandler({
            EntityNotFoundException.class,
            DuplicateKeyException.class
    })
    public ResponseEntity handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError(e));
    }

    @ExceptionHandler({
            NoSuchElementException.class,
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity handleBadRequests(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException) {
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) exception).getFieldErrors();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body((fieldErrors.stream()
                            .map(DadosErroValidacao::new)
                            .sorted((Comparator.comparing(o -> o.field)))
                            .toList()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(exception));
    }

    private record DadosErroValidacao(String field, String message) {
        public DadosErroValidacao(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    private record ResponseError(String message) {
        public ResponseError(Exception exception) {
            this(exception.getMessage());
        }
    }
}
