package com.tk.app.common.exception;

import com.tk.app.common.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tank198435163.com
 */
@RestControllerAdvice
public class GlobalExpHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseBody handleControllerExp(HttpServletRequest req, Exception e) {
    ResponseBody responseBody = new ResponseBody();
    responseBody.putIfAbsent("error", e.getMessage());
    responseBody.putIfAbsent("status", 500);
    return responseBody;
  }
}
