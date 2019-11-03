package com.tk.app.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * @author tank198435163.com
 */
@Slf4j
@Comment
@Component
public class JsonUtil {

  public <T> Optional<String> toJsonStr(@NonNull T body) {
    try {
      String jsonStr = this.jsonMapper.writeValueAsString(body);
      return Optional.ofNullable(jsonStr);
    } catch (JsonProcessingException e) {
      log.error("将对象序列化json异常:[{}]", e.getMessage());
      return Optional.empty();
    }
  }

  public <T> Optional<T> toObject(@NonNull String jsonStr, Class<T> clazz) {
    try {
      T obj = this.jsonMapper.readValue(jsonStr.getBytes(), clazz);
      return Optional.ofNullable(obj);
    } catch (IOException e) {
      log.error("将json字符串转成对象异常:[{}]", e.getMessage());
      return Optional.empty();
    }
  }


  @Autowired
  private ObjectMapper jsonMapper;
}
