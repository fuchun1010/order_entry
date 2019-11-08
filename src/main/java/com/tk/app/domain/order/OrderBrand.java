package com.tk.app.domain.order;


import com.tk.app.common.Comment;

/**
 * @author tank198435163.com
 */
@Comment(desc = "订单品牌")
public enum OrderBrand {

  PAGODA((short) 100, "白果园"),
  GUODUOMEI((short) 200, "果多美");

  OrderBrand(short value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public short value;

  public String desc;
}
