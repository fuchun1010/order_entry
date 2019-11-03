package com.tk.app.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tank198435163.com
 */
@Configuration
public class JsonCfg {

  @Bean
  public ObjectMapper initJsonObjectMapper() {
    return new ObjectMapper();
  }

}
