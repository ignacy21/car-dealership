package pl.zajavka.domain.exception;


public class ProcessingException extends NotFoundException {

    public ProcessingException(String message) {
       super(message);
    }
}
