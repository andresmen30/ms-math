package ms.math.infraestructure.exception;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;
import ms.math.application.response.ApiResponse;
import ms.math.domain.exception.PercentageServiceUnavailableException;
import ms.math.infraestructure.util.ResponseUtil;

@RestControllerAdvice
public class ExceptionHelper {

   @ExceptionHandler(PercentageServiceUnavailableException.class)
   public ApiResponse handlePercentageServiceUnavailable(PercentageServiceUnavailableException ex) {
      return ResponseUtil.response(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected ApiResponse handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
      final List<String> details = new ArrayList<>();
      ex.getBindingResult().getAllErrors().forEach(error -> {
         final String detail = StringUtils.join(((FieldError) error).getField(), " : ", Objects.requireNonNull(error.getDefaultMessage()));
         details.add(detail);
      });
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, details.toString());
   }

   @ExceptionHandler(ConstraintViolationException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected ApiResponse handleConstraintViolationException(final ConstraintViolationException ex) {
      final List<String> details = new ArrayList<>();
      ex.getConstraintViolations().forEach(constraintViolation -> {
         final String detail = StringUtils.join(constraintViolation.getPropertyPath(), " : ", constraintViolation.getMessage());
         details.add(detail);
      });
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, details.toString());
   }

   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected ApiResponse handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex) {
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, ex.getMessage());
   }

   @ExceptionHandler(SQLException.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   protected ApiResponse handleSqlException(final SQLException ex) {
      return ResponseUtil.response(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
   }

   @ExceptionHandler(HttpMessageNotReadableException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected ApiResponse handleMessageReadable(final HttpMessageNotReadableException ex) {
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, ex.getMessage());

   }

   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   protected ApiResponse handleException(final Exception ex) {
      return ResponseUtil.response(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
   }

}
