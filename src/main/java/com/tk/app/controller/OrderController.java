package com.tk.app.controller;

import com.tk.app.common.Comment;
import com.tk.app.common.ResponseBody;
import com.tk.app.message.OrderReq;
import com.tk.app.message.order.response.CreatedOrderRes;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tk.app.common.UrlPattern.*;

/**
 * @author tank198435163.com
 */
@Slf4j
@RestController
@Comment(desc = "订单处理控制器")
@RequestMapping(path = URL_PREFIX)
public class OrderController {

  @Comment(desc = "创建订单")
  @PostMapping(path = ORDER_CREATED)
  public ResponseEntity<CreatedOrderRes> createOrder(@RequestBody @NonNull final OrderReq order) {
    log.info("创建订单:[{}]", order.getCustomerId());
    CreatedOrderRes createdOrderRes = new CreatedOrderRes();
    createdOrderRes.setOrderNo(100L);
    return ResponseEntity.ok(createdOrderRes);
  }

  @Comment(desc = "按照订单id查询订单")
  @GetMapping(path = ORDER_QUERY_BY_OrderNo)
  public ResponseEntity<ResponseBody> queryOrderByOrderNo(@PathVariable(name = "orderNo") final Long orderNo) {
    ResponseBody responseBody = new ResponseBody();
    responseBody.add("customerId", "xxx");
    return ResponseEntity.ok(responseBody);
  }


}
