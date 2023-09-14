package net.example.postaltrackingservice.controller;

import lombok.extern.slf4j.Slf4j;
import net.example.postaltrackingservice.exception.EventRegistrationException;
import net.example.postaltrackingservice.exception.PostOfficeNotFoundException;
import net.example.postaltrackingservice.exception.PostageStatusNotFoundException;
import net.example.postaltrackingservice.exception.PostalItemNotFoundException;
import net.example.postaltrackingservice.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EventRegistrationException.class)
    public ErrorDto processingException(EventRegistrationException ex) {
        return errorMessage(ex);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostalItemNotFoundException.class)
    public ErrorDto processingException(PostalItemNotFoundException ex) {
        return errorMessage(ex);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostageStatusNotFoundException.class)
    public ErrorDto processingException(PostageStatusNotFoundException ex) {
        return errorMessage(ex);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostOfficeNotFoundException.class)
    public ErrorDto processingException(PostOfficeNotFoundException ex) {
        return errorMessage(ex);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto processingException(MethodArgumentNotValidException ex) {
        return errorMessage(ex);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorDto processingException(AccessDeniedException ex) {
        return errorMessage(ex);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto processingException(Exception ex) {
        return errorMessage(ex);
    }

    private ErrorDto errorMessage(Exception ex) {
        log.error(ex.getMessage());
        return new ErrorDto(ex.getMessage());
    }

}
