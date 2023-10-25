package pl.zajavka.domain.exception;


import jakarta.ws.rs.NotFoundException;

public class ProcessingException extends NotFoundException {

    public ProcessingException(String message) {
       super(message);
    }
}
