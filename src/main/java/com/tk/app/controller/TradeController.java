package com.tk.app.controller;

import com.annimon.stream.Optional;
import com.tk.app.OrderConfirmHeaderDTO;
import com.tk.app.common.Comment;
import com.tk.app.common.JsonUtil;
import com.tk.app.common.mq.Sender;
import com.tk.app.message.Demo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.tk.app.common.UrlPattern.*;

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
  public ResponseEntity<String> create(@NonNull @RequestBody Demo demo) {
    this.jsonUtil.toJsonStr(demo).ifPresent(str -> {
      String topic = "orderTopic";
      String tag = "order";
      this.sender.sendSyncWithTag(topic, tag, str);
      log.info("发送消息到topic:[{}],标签:[{}]成功", topic, tag);

    });
    return ResponseEntity.ok().body("create new traded order");
  }

  @PostMapping(path = CONFIRM_ORDER)
  public ResponseEntity<OrderConfirmHeaderDTO> confirm(@RequestHeader Map<String, Object> appInfo,
                                                       @RequestBody Map<String, Object> paylaod) {
    OrderConfirmHeaderDTO dto = Optional.ofNullable(appInfo.get("x-defined-appinfo"))
        .select(String.class)
        .flatMap(jsonStr -> this.jsonUtil.toObject(jsonStr, OrderConfirmHeaderDTO.class))
        .orElseThrow(InstantiationError::new);

    return ResponseEntity.ok(dto);
  }

  @Autowired
  private Sender sender;

  @Autowired
  private JsonUtil jsonUtil;
}
