package pl.wroblewski.simplyaccounting.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.StringJoiner;


@ControllerAdvice
public class RestExceptionHandler {

    Logger logger = LoggerFactory.getLogger("Request exception");

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> handle(ObjectNotFoundException e) {
        logger.info(e.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(ObjectReferenceException.class)
    public ResponseEntity<String> handle(ObjectReferenceException e) {
        logger.info(e.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(IncorrectDateException.class)
    public ResponseEntity<String> handle(IncorrectDateException e) {
        logger.info(e.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<String> handle(ObjectAlreadyExistsException e) {
        logger.info(e.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<String> handle(UsernameTakenException e) {
        logger.info(e.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        logger.info(e.getMessage());
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Incorrect request body.");
        e.getBindingResult().getFieldErrors().forEach(o ->
                joiner.add(new StringBuilder()
                        .append("Field \"")
                        .append(o.getField())
                        .append("\" ")
                        .append(o.getDefaultMessage())
                        .append(" but has value ")
                        .append(o.getRejectedValue())
                        .append(".")
                )

        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(joiner.toString());
    }

}
