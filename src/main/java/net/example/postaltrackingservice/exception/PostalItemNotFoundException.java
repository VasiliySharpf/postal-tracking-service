package net.example.postaltrackingservice.exception;

public class PostalItemNotFoundException extends RuntimeException {

    public PostalItemNotFoundException(String message) {
        super(message);
    }
}
