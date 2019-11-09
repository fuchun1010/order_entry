package com.tk.app.domain.order;


import com.tk.app.common.Comment;

/**
 * @author tank198435163.com
 */
@Comment(desc = "普通下单购")
public class NormalOrderComposed extends OrderComposed {

  @Override
  public Integer limitConsumers() {
    return 1;
  }
}
