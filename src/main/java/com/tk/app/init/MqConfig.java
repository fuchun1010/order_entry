package com.tk.app.init;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tank198435163.com
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mq.rocketmq")
public class MqConfig {

  private String group;

  private String nameSvr;

  private String consumerGroup;

}
