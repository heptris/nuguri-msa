package com.nuguri.dealservice.exception;

import com.nuguri.dealservice.dto.response.ResponseDto;
import com.nuguri.dealservice.exception.dto.ApiErrorResponse;
import com.nuguri.dealservice.exception.ex.CustomException;
import com.nuguri.dealservice.exception.ex.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";
    private static final String INTERNAL_SERVER_ERROR_CODE = "S0001";

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> customValidation(CustomValidationException e) {

        return new ResponseEntity<ResponseDto>(new ResponseDto(400, e.getMessage(),

                e.getErrorMap()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {

        return new ResponseEntity<>(new ResponseDto(e.getErrorCode().getStatus(), e.getErrorCode().getMessage(), null),

                HttpStatus.valueOf(e.getErrorCode().getStatus()));

    }

//    @ExceptionHandler(ApplicationException.class)
//    public ResponseEntity<ApiErrorResponse> applicationException(ApplicationException e) {
//        String errorCode = e.getErrorCode();
//        log.warn(
//                LOG_FORMAT,
//                e.getClass().getSimpleName(),
//                errorCode,
//                e.getMessage()
//        );
//        return ResponseEntity
//                .status(e.getHttpStatus())
//                .body(new ApiErrorResponse(errorCode));
//    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse> dataAccessException(DataAccessException e) {
        log.error(
                LOG_FORMAT,
                e.getClass().getSimpleName(),
                INTERNAL_SERVER_ERROR_CODE,
                e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(INTERNAL_SERVER_ERROR_CODE));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> runtimeException(RuntimeException e) {
        log.error(
                LOG_FORMAT,
                e.getClass().getSimpleName(),
                INTERNAL_SERVER_ERROR_CODE,
                e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(INTERNAL_SERVER_ERROR_CODE));
    }

}
