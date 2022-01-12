package fxs.fourthten.springtutorial.config.exception;

import fxs.fourthten.springtutorial.domain.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(HttpServletResponse response, CustomException exception) throws IOException {
        ResponseDto<Map> responseDto = new ResponseDto(exception.getHttpStatus().value(), exception.getMessage(), null);
        return new ResponseEntity<>(responseDto, exception.getHttpStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(HttpServletResponse response, ValidationException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Validation required", details);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(HttpServletResponse response, MissingServletRequestParameterException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Missing servlet parameter", details);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Object> handleMissingServletRequestPartException(HttpServletResponse response, MissingServletRequestPartException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Missing servlet part", details);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(HttpServletResponse response, MethodArgumentTypeMismatchException exception) throws IOException {
        Map details = new HashMap();
        details.put("name", exception.getName());
        details.put("value", ofNullable(exception.getValue()).map(Object::toString).orElse(""));
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Method mismatch", details);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(HttpServletResponse response, MethodArgumentNotValidException exception) throws IOException {
        Map detailEx = new HashMap<>();
        List<Map> details = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(field -> {
                Map detail = new HashMap();
                detail.put("name", field.getObjectName());
                detail.put("field", field.getField());
                detail.put("value", field.getRejectedValue());
                detail.put("message", field.getDefaultMessage());
                details.add(detail);
            });
        detailEx.put("error", details);
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Method not valid", detailEx);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(HttpServletResponse response, AccessDeniedException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(FORBIDDEN.value(), "Access denied", details);
        return new ResponseEntity<>(responseDto, FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(HttpServletResponse response, Exception exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(INTERNAL_SERVER_ERROR.value(), "Something went wrong", details);
        return new ResponseEntity<>(responseDto, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpServletResponse response, HttpMessageNotReadableException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(FORBIDDEN.value(), "Not readable", details);
        return new ResponseEntity<>(responseDto, FORBIDDEN);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpServletResponse response, HttpRequestMethodNotSupportedException exception) throws IOException {
        Map details = new HashMap();
        details.put("method", exception.getMethod());
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Method not supported", details);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotAcceptableException(HttpServletResponse response, HttpMediaTypeNotAcceptableException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Media type not accepted", details);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpServletResponse response, HttpMediaTypeNotSupportedException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Media type not supported", details);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(HttpServletResponse response, NoHandlerFoundException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(NOT_FOUND.value(), "No handler found", details);
        return new ResponseEntity<>(responseDto, NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(HttpServletResponse response, MaxUploadSizeExceededException exception) throws IOException {
        Map details = new HashMap();
        details.put("message", exception.getMessage());
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Max upload", details);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(HttpServletResponse response, BindException exception) throws IOException {
        Map detailEx = new HashMap<>();
        List<Map> details = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(field -> {
            Map detail = new HashMap();
            detail.put("name", field.getObjectName());
            detail.put("field", field.getField());
            detail.put("value", field.getRejectedValue());
            detail.put("message", field.getDefaultMessage());
            details.add(detail);
        });
        detailEx.put("error", details);
        ResponseDto<Map> responseDto = new ResponseDto(BAD_REQUEST.value(), "Something error", detailEx);
        return new ResponseEntity<>(responseDto, BAD_REQUEST);
    }
}
