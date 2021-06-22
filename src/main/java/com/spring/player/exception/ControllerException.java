package com.spring.player.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler
    public ResponseEntity<PlayerError> getExceptionNotFound(PlayerExxception playerExxception){
        PlayerError playerError = new PlayerError();
        playerError.setStatusCode(HttpStatus.NOT_FOUND.value());
        playerError.setMessage(playerExxception.getMessage());
        playerError.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<PlayerError>(playerError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PlayerError> getException(Exception e){
        PlayerError playerError = new PlayerError();
        playerError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        playerError.setMessage(e.getMessage());
        playerError.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<PlayerError>(playerError, HttpStatus.BAD_REQUEST);
    }
}
