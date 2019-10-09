package com.tk.app.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Sets;
import com.tk.app.common.Comment;
import com.tk.app.common.deseria.OrderDeserialization;
import com.tk.app.message.item.ItemReq;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "订单请求的基类")
@JsonDeserialize(using = OrderDeserialization.class)
public abstract class OrderReq {

  @JsonIgnore
  public void addItem(@NonNull final ItemReq itemReq) {
    this.items.add(itemReq);
  }

  @Comment(desc = "用户id")
  private String customerId;

  @Comment(desc = "订单入口")
  private String entry;

  @Comment(desc = "订单明细")
  private Set<ItemReq> items = Sets.newHashSet();

}
