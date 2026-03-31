package com.codingcat.cafekiosk.module;

import com.codingcat.cafekiosk.module.model.ApiResponseVo;
import java.net.BindException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResponseVo<Object> bindException(MethodArgumentNotValidException e){
    log.debug("Validation error: {}", e.getMessage());
    String binding = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    return ApiResponseVo.of(HttpStatus.BAD_REQUEST
      ,binding
      ,null
    );
  }

}
