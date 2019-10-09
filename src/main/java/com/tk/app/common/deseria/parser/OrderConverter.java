package com.tk.app.common.deseria.parser;

import com.tk.app.common.exception.NotSupportExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author tank198435163.com
 */
@Component
public class OrderConverter {

  public OrderParser selectOrderParser(final String entry) throws NotSupportExp {
    OrderParser orderParser = this.orderParserSelector.get(entry);
    if (orderParser == null) {
      throw new NotSupportExp(String.format("[%s]订单解析不支持", entry));
    }
    return orderParser;
  }


  @Autowired
  @Qualifier("registerOrderParser")
  private Map<String, OrderParser> orderParserSelector;
}
