package com.tk.app.common.deseria.parser;


import com.fasterxml.jackson.databind.JsonNode;
import com.tk.app.message.OrderReq;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author tank198435163.com
 */
@Component
@Qualifier("enjoyOrderParser")
public class EnjoyOrderParser implements OrderParser {
  @Override
  public Optional<OrderReq> parse(@NonNull JsonNode rootNode) {
    return Optional.empty();
  }
}
