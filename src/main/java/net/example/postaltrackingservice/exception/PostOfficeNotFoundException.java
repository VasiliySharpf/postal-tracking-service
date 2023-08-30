package net.example.postaltrackingservice.exception;

public class PostOfficeNotFoundException extends RuntimeException {

    public PostOfficeNotFoundException(String message) {
        super(message);
    }
}
