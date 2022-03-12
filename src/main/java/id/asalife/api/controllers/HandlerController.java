package id.asalife.api.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import id.asalife.api.domains.models.ApiError;
import id.asalife.api.domains.models.ErrorResponse;
import id.asalife.api.domains.models.ValidationErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

public class HandlerController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        HashMap<String, HashMap<String, String>> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            HashMap<String, String> value = new HashMap<>();

            value.put("code", error.getCode());
            value.put("message", errorMessage);
            errors.put(fieldName, value);
        });
        return new ValidationErrorResponse<>(errors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ResponseStatusException ex) {
        ApiError apiError = new ApiError(ex.getReason(), ex.getRawStatusCode());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getCode());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> invalidFormatException(final InvalidFormatException e) {
        ApiError apiError = new ApiError("Invalid Format", 400);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getCode());
    }
}
