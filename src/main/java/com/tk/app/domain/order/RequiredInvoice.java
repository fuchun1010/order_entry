package com.tk.app.domain.order;

import com.tk.app.common.Comment;

/**
 * @author tank198435163.com
 */
@Comment(desc = "是否开具发票")
public enum RequiredInvoice {

  YES((short) 100, "需要开发发票"), NO((short) 200, "拒绝开发票");

  RequiredInvoice(short value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public short value;

  public String desc;
}
