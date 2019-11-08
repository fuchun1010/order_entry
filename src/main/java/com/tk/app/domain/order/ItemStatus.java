package com.tk.app.domain.order;

import com.tk.app.common.Comment;

/**
 * @author tank198435163.com
 */
@Comment(desc = "果品状态")
public enum ItemStatus {

  CANCEL(0, "取消"),
  NORMAL(1, "正常"),
  EXCHANGE(2, "换货"),
  REFUND(3, "退货");

  ItemStatus(int value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public int value;
  public String desc;

}
