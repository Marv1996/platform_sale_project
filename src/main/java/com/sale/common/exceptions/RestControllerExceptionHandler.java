package com.sale.common.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlatformBadRequestException.class)
    protected @ResponseBody ResponseEntity<ExceptionResponse> badRequestExceptionHandler(
            PlatformBadRequestException ex
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(), ApplicationErrorCode.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(PlatformApiException.class)
    protected @ResponseBody ResponseEntity<ExceptionResponse> apiExceptionHandler(
            PlatformApiException ex
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(), ApplicationErrorCode.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(PlatformNotFoundException.class)
    protected @ResponseBody ResponseEntity<ExceptionResponse> notFoundExceptionHandler(
            PlatformNotFoundException ex
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(), ApplicationErrorCode.NOT_FOUND
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(PlatformUnauthorizedException.class)
    protected @ResponseBody ResponseEntity<ExceptionResponse> unauthorizedExceptionHandler(
            PlatformUnauthorizedException ex
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(), ApplicationErrorCode.UNAUTHORISED
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> details = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        ExceptionResponse exceptionResponse =
                new ExceptionResponse("Invalid request", ApplicationErrorCode.BAD_REQUEST, details);
        return ResponseEntity.status(status).body(exceptionResponse);
    }
}
