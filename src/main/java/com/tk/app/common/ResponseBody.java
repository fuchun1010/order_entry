package com.tk.app.common;

import lombok.NonNull;

import java.util.TreeMap;

/**
 * @author tank198435163.com
 */
public class ResponseBody extends TreeMap<String, Object> {

  public <T> void add(@NonNull final String key, @NonNull final T data) {
    this.remove(key);
    this.putIfAbsent(key, data);
  }
}
