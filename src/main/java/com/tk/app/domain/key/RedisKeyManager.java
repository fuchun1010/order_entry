package com.tk.app.domain.key;

import com.tk.app.common.Comment;
import lombok.NonNull;

/**
 * @author tank198435163.com
 */
@Comment(desc = "redis对应key的管理工具")
public class RedisKeyManager {

  @Comment(desc = "原始订单key")
  public String entireOrderKey(@NonNull Long id) {
    if (id < 0 || id > Long.MAX_VALUE) {
      throw new IllegalArgumentException("id超出Long的最大或者最小范围限制");
    }
    return String.format("raw:order:%d", id).toUpperCase();
  }


}
