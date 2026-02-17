package com.backend.ProductService.Exception;

import com.backend.ProductService.Exception.Dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidationExceptions(MethodArgumentNotValidException e,HttpServletRequest request){
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));

        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errors)
                .path(request.getRequestURI())
                .build();
    }

    // I have Hard coded the error message
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public Map<String,String> handleJsonParsingErrors(HttpMessageNotReadableException e){
//        Map<String,String> errors = new HashMap<>();
//        if(e.getCause() instanceof InvalidFormatException IFE && IFE.getTargetType().isEnum())
//             errors.put("status","The Valid status are DRAFT ACTIVE ARCHIVED");
//
//        else
//            errors.put("error","Check the JSON Format");
//        return errors;
//    }

    //I made the error message dynamic
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public Map<String,String> handleJsonParsingErrors(HttpMessageNotReadableException e){
        Map<String,String> errors = new HashMap<>();
        if(e.getCause() instanceof InvalidFormatException IFE && IFE.getTargetType().isEnum())
        {
            String fieldName = IFE.getPath().get(IFE.getPath().size() - 1).getFieldName();
            String validValues = Arrays.toString(IFE.getTargetType().getEnumConstants());
            errors.put(fieldName,"Value is invalid. Valid Options are " + validValues);
        }

        else
            errors.put("error","Check the JSON Format");
        return errors;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleProductNotFoundException(ProductNotFoundException e, HttpServletRequest request)
    {
       return ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(ProductImageValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleProductImageValidationException(ProductImageValidationException e, HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }


}
