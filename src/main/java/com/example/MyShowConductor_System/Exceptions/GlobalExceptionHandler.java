package com.example.MyShowConductor_System.Exceptions;

import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.Payloads.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.mail.MessagingException;
import javax.naming.CommunicationException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

@Slf4j

@RestControllerAdvice
public class GlobalExceptionHandler {
//https://sl.bing.net/cEEiQObYFA4

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse =   new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String, String> result= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            result.put(fieldName, message);
        });
        ApiResponse apiResponse =   new ApiResponse("Invalid Input Error",false,new ResponseError<>(result));
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(value = {CannotCreateTransactionException.class})
//    https://stackoverflow.com/questions/72665235/how-to-catch-connection-refused-exception-of-spring-boot-data-jpa
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ApiResponse> cannotCreateTransactionExceptionHandler(CannotCreateTransactionException ex, WebRequest request) {
        if (ex.contains(ConnectException.class)) {
            log.error("DB ConnectException :  {}", ex.getMessage());
            String message = "Database Server Down";
            return new ResponseEntity<>(new ApiResponse(message,false, new ResponseError<>(ex.getMessage())),HttpStatus.SERVICE_UNAVAILABLE);
        }else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return  new ResponseEntity<>(new ApiResponse("Internal Server Error",false,new ResponseError<>(ex.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ApiResponse> MessagingExceptionHandler(MessagingException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ApiResponse<>(message,false),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> runtimeExceptionHandler(RuntimeException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse =   new ApiResponse("Unexpected Exception occur:"+ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> UnexpectedExceptionHandler(Exception ex){
        String message = ex.getMessage();
        ApiResponse apiResponse =   new ApiResponse("Unexpected Exception occur:"+ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }



//    also add one for bad routing which is not exist
//    beauty of the @ExceptionHandler and @RestControllerAdvice is we just have to define exception handler that will be called when the exception is thrown and will be handled by the exception handler automatically( we dont have to catch in service or even in controller as thrown exceptions will come in controoler at end and we all exceptions in controller are handled automatically)
}

//SQLIntegrityConstraintViolationException  if duplicate entries
// SQLException  if database structure gone wrong or something like extra column created (Field 'user_id' doesn't have a default value)
// RequestRejectedException
//and we'll defne one excepetio that will be called if all this fails and give message unexoected error occur you can contact us
//eg runtime at last or compiletime exception
//also unknown exception and unexpected exception