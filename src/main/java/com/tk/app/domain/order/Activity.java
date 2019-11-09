package com.tk.app.domain.order;

import com.google.common.base.Objects;
import com.tk.app.common.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "活动")
@EqualsAndHashCode(callSuper = false)
public class Activity {

  @Comment(desc = "活动编号")
  private String activityNo;

  @Comment(desc = "活动编号")
  private String name;

  @Comment(desc = "门店支付百分比")
  private String storePercent;

  @Comment(desc = "集团支付百分比")
  private String groupPercent;

  @Comment(desc = "第三方支付百分比")
  private String thirdPercent;

  @Comment(desc = "活动的目标对象，目标对象可以是订单，取消，用户，果品")
  private String targetCode;

  @Comment(desc = "活动内别")
  private ActivityType activityType;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Activity)) {
      return false;
    }
    Activity activity = (Activity) o;
    return Objects.equal(getActivityNo(), activity.getActivityNo());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getActivityNo());
  }
}
