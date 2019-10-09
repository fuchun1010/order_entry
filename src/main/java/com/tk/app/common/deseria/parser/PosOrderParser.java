package com.tk.app.common.deseria.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.tk.app.common.Comment;
import com.tk.app.message.OrderReq;
import com.tk.app.message.item.ItemReq;
import com.tk.app.message.order.request.CreatedPosOrderReq;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author tank198435163.com
 */
@Component
@Qualifier("posOrderParser")
@Comment(desc = "pos订单解析器")
public class PosOrderParser implements OrderParser {

  @Override
  public Optional<OrderReq> parse(@NonNull JsonNode rootNode) {
    CreatedPosOrderReq createdPosOrderReq = new CreatedPosOrderReq();
    val customerId = rootNode.get("customerId").textValue();
    val entry = rootNode.get("entry").textValue();
    val items = rootNode.get("items");
    createdPosOrderReq.setCustomerId(customerId);
    createdPosOrderReq.setEntry(entry);
    for (JsonNode item : items) {
      val itemCode = item.get("itemCode").textValue();
      val itemName = item.get("itemName").textValue();
      val quality = item.get("quality") == null ? 0.0d : item.get("quality").doubleValue();
      val unitPrice = item.get("unitPrice") == null ? 0.0d : item.get("unitPrice").doubleValue();
      ItemReq itemReq = new ItemReq(itemCode, itemName, quality, unitPrice);
      createdPosOrderReq.addItem(itemReq);
    }
    return Optional.of(createdPosOrderReq);
  }
}
