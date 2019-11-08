package com.tk.app.domain.order;

import com.tk.app.common.Comment;

/**
 * @author tank198435163.com
 */
@Comment(desc = "订单类型")
public enum OrderType {


  O2O((short) 100, "O2O"), B2C((short) 200, "B2C(心享)");

  OrderType(short value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public short value;

  public String desc;
}
