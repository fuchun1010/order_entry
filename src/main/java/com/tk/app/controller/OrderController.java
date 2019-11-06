package com.tk.app.controller;

import com.tk.app.common.Comment;
import com.tk.app.common.Constants;
import com.tk.app.common.ResponseBody;
import com.tk.app.common.holder.DbHolder;
import com.tk.app.common.interceptor.DbSelector;
import com.tk.app.message.OrderReq;
import com.tk.app.message.mock.MockOrder;
import com.tk.app.message.order.response.CreatedOrderRes;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

  @Comment(desc = "模拟测试")
  @GetMapping(path = MOCK_EXP)
  public ResponseEntity<ResponseBody> mockExp() throws Exception {
    throw new Exception("exp");
  }

  @Comment(desc = "welcome")
  @GetMapping(path = WELCOME)
  @DbSelector
  public ResponseEntity<ResponseBody> welcome() {
    ResponseBody responseBody = new ResponseBody();
    responseBody.add("desc", "welcome to Order entry");
    return ResponseEntity.ok(responseBody);
  }

  @Comment(desc = "mock test aop select db and mapped table with orderId")
  @PostMapping(path = MOCK_ORDER_CREATED)
  @DbSelector
  @SneakyThrows
  public ResponseEntity<ResponseBody> pushOrder(@RequestBody @NonNull final MockOrder mockOrder) {
    final val dbName = DbHolder.fetchSelectedDb();
    final String tableName = DbHolder.fetchSelectedTable().orElse(Constants.EMPTY_STR);
    ResponseBody responseBody = new ResponseBody();
    responseBody.add("db", dbName);
    responseBody.add("tableName", tableName);
    return ResponseEntity.ok(responseBody);
  }
}
