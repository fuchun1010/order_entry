package com.tk.app.common.deseria.parser;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author tank198435163.com
 */
@Configuration
public class OrderConvertConfig {

  /**
   * 注册订单解析器
   *
   * @return
   */
  @Bean("registerOrderParser")
  public Map<String, OrderParser> registerOrderParser() {
    final Map<String, OrderParser> orderRegister = Maps.newHashMap();

    orderRegister.putIfAbsent("enjoy", this.enjoyOrderParser);
    orderRegister.putIfAbsent("pos", this.posOrderParser);

    return orderRegister;
  }

  @Autowired
  @Qualifier("enjoyOrderParser")
  private OrderParser enjoyOrderParser;

  @Autowired
  @Qualifier("posOrderParser")
  private OrderParser posOrderParser;

}
