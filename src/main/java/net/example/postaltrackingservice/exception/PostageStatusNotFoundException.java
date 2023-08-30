package net.example.postaltrackingservice.exception;

public class PostageStatusNotFoundException extends RuntimeException {

    public PostageStatusNotFoundException(String message) {
        super(message);
    }
}
