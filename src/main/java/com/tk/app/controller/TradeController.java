package com.tk.app.controller;

import com.tk.app.common.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tk.app.common.UrlPattern.TRADE_CREATED;
import static com.tk.app.common.UrlPattern.URL_PREFIX;

/**
 * @author tank198435163.com
 */
@Slf4j
@Component
@Comment(desc = "交易")
@RestController
@RequestMapping(path = URL_PREFIX)
public class TradeController {


  @PostMapping(path = TRADE_CREATED)
  public ResponseEntity<String> create() {
    return ResponseEntity.ok().body("create new traded order");
  }

}
