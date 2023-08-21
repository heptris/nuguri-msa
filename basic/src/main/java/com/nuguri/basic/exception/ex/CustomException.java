package com.nuguri.basic.exception.ex;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

}
