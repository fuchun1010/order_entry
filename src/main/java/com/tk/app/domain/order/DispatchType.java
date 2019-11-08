package com.tk.app.domain.order;

import com.tk.app.common.Comment;

/**
 * @author tank198435163.com
 */

@Comment(desc = "配送方式")
public enum DispatchType {


  REALTIME((short) 100, "及时达"),
  RESERVATION((short) 200, "预定单"),
  PICKUP((short) 300, "自提单"),
  GROUP((short) 400, "团购");

  DispatchType(short value, String type) {
    this.value = value;
    this.type = type;
  }

  public short value;

  public String type;
}
