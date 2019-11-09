package com.tk.app.domain.order;


import com.tk.app.common.Comment;
import lombok.NonNull;

/**
 * @author tank198435163.com
 */
@Comment(desc = "团购")
public class GroupOrderComposed extends OrderComposed {

  public GroupOrderComposed(final @NonNull Integer max) {
    super();
    this.limit = max;
  }

  @Override
  public Integer limitConsumers() {
    return this.limit;
  }

  private Integer limit = 1;
}
