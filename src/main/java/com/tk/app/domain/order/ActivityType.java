package com.tk.app.domain.order;

import com.tk.app.common.Comment;
import lombok.NonNull;

/**
 * @author tank198435163.com
 */
@Comment(desc = "活动类型")
public enum ActivityType {

  ACTIVITY(100, "活动"), COUPON(200, "优惠券"), COIN(300, "积分");

  ActivityType(@NonNull final Integer value, @NonNull final String desc) {
    this.value = value;
    this.desc = desc;
  }

  public Integer value;
  public String desc;
}
