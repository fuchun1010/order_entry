package com.tk.app.controller;

import com.tk.app.common.Comment;
import com.tk.app.common.JsonUtil;
import com.tk.app.common.mq.Sender;
import com.tk.app.message.Demo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<String> create(@NonNull @RequestBody Demo demo) {
    this.jsonUtil.toJsonStr(demo).ifPresent(str -> {
      String topic = "orderTopic";
      String tag = "order";
      this.sender.sendSyncWithTag(topic, tag, str);
      log.info("发送消息到topic:[{}],标签:[{}]成功", topic, tag);

    });
    return ResponseEntity.ok().body("create new traded order");
  }

  @Autowired
  private Sender sender;

  @Autowired
  private JsonUtil jsonUtil;
}
