package com.tk.app.common.deseria.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.tk.app.message.OrderReq;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author tank198435163.com
 */
public interface OrderParser {

  /**
   * @param rootNode
   * @return
   */
  Optional<OrderReq> parse(@NonNull final JsonNode rootNode);

}
