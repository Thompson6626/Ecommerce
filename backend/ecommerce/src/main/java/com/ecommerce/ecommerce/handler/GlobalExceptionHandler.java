package com.ecommerce.ecommerce.handler;

import com.ecommerce.ecommerce.exceptions.IncorrectPasswordException;
import com.ecommerce.ecommerce.exceptions.NewPasswordDoesNotMatchException;
import com.ecommerce.ecommerce.exceptions.UnauthorizedModificationException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.stripe.exception.StripeException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.ApiException;

import java.util.HashSet;
import java.util.Set;

import static com.ecommerce.ecommerce.handler.BussinesErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp){
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse.builder()
                        .businessErrorCode(ACCOUNT_LOCKED.getCode())
                        .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp){
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse.builder()
                        .businessErrorCode(ACCOUNT_DISABLED.getCode())
                        .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp){
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse.builder()
                        .businessErrorCode(BAD_CREDENTIALS.getCode())
                        .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                        .error(BAD_CREDENTIALS.getDescription())
                        .build()
        );
    }
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleException(IncorrectPasswordException exp){
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .businessErrorCode(INCORRECT_CURRENT_PASSWORD.getCode())
                        .businessErrorDescription(INCORRECT_CURRENT_PASSWORD.getDescription())
                        .error(INCORRECT_CURRENT_PASSWORD.getDescription())
                        .build()
        );
    }

    @ExceptionHandler(NewPasswordDoesNotMatchException.class)
    public ResponseEntity<ExceptionResponse> handleException(NewPasswordDoesNotMatchException exp){
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .businessErrorCode(NEW_PASSWORD_DOES_NOT_MATCH.getCode())
                        .businessErrorDescription(NEW_PASSWORD_DOES_NOT_MATCH.getDescription())
                        .error(NEW_PASSWORD_DOES_NOT_MATCH.getDescription())
                        .build()
        );
    }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();
        exp.getBindingResult()
                .getAllErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));

        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .validationErrors(errors)
                        .build()
        );
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException exp) {
        return ResponseEntity.status(NOT_FOUND).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }
    @ExceptionHandler(UnauthorizedModificationException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedModificationException(UnauthorizedModificationException exp) {
        return ResponseEntity.status(FORBIDDEN).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }
    @ExceptionHandler(CardException.class)
    public ResponseEntity<ExceptionResponse> handleCardException(CardException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRequestException(InvalidRequestException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException exp) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ApiConnectionException.class)
    public ResponseEntity<ExceptionResponse> handleApiConnectionException(ApiConnectionException exp) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> handleApiException(ApiException exp) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<ExceptionResponse> handleStripeException(StripeException exp) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp){
        exp.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .businessErrorDescription("Internal server error")
                        .error(exp.getMessage())
                        .build()
        );
    }

}
