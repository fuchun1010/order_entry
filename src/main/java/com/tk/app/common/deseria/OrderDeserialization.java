package com.tk.app.common.deseria;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tk.app.common.Comment;
import com.tk.app.common.deseria.parser.OrderConverter;
import com.tk.app.common.deseria.parser.OrderParser;
import com.tk.app.common.exception.NotSupportExp;
import com.tk.app.message.OrderReq;
import com.tk.app.message.order.CreatedEmptyOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.tk.app.common.Constants.EMPTY_STR;

/**
 * @author tank198435163.com
 */
@Slf4j
@Component
@Comment(desc = "订单反序列化")
public class OrderDeserialization extends StdDeserializer<OrderReq> {

  public OrderDeserialization() {
    this(OrderReq.class);
  }

  public OrderDeserialization(Class<?> vc) {
    super(vc);
  }

  @Override
  public OrderReq deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
    JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
    String entry = rootNode.get("entry") != null ? rootNode.get("entry").textValue() : EMPTY_STR;
    if (EMPTY_STR.equalsIgnoreCase(entry)) {
      entry = "third";
    }
    try {
      OrderParser orderParser = this.orderConvert.selectOrderParser(entry);
      return orderParser.parse(rootNode).orElse(new CreatedEmptyOrder());
    } catch (NotSupportExp notSupportExp) {
      log.error("解析[{}]订单异常", entry);
    }

    return new CreatedEmptyOrder();
  }

  @Autowired
  private OrderConverter orderConvert;
}
