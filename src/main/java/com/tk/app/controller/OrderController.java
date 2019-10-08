package com.tk.app.controller;

import com.tk.app.common.Comment;
import com.tk.app.common.ResponseBody;
import com.tk.app.message.OrderReq;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tk.app.common.Constants.ORDER_CREATED;
import static com.tk.app.common.Constants.URL_PREFIX;

/**
 * @author tank198435163.com
 */
@Comment(desc = "订单处理控制器")
@RestController
@RequestMapping(path = URL_PREFIX)
public class OrderController {

  @Comment(desc = "创建订单")
  @PostMapping(path = ORDER_CREATED)
  public ResponseEntity<ResponseBody> createOrder(@RequestBody @NonNull final OrderReq order) {
    final ResponseBody responseBody = new ResponseBody();
    responseBody.add("orderId", 1);
    responseBody.add("status", 200);
    return ResponseEntity.ok(responseBody);
  }

}
