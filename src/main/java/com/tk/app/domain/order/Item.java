package com.tk.app.domain.order;

import com.annimon.stream.Stream;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tk.app.common.Comment;
import lombok.Data;
import lombok.NonNull;
import org.testng.collections.Sets;

import java.util.Objects;
import java.util.Set;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "果品")
public class Item {

  @Comment(desc = "果品名称")
  private String name;

  @Comment(desc = "果品code")
  private String itemCode;

  @Comment(desc = "果品唯一编码,因为同一个果品编码可能对于对个名称,这个是用户下单的时候item+果品名的md5码，格式item_xxxx")
  private String uniqueItemCode;

  @Comment(desc = "实际交易果品code,除了换购,一般情况下都是itemCode == realItemCode")
  private String realItemCode;

  @Comment(desc = "单价")
  private String unitPrice;

  @Comment(desc = "重量")
  private String weight;

  @Comment(desc = "折扣价")
  private String disCount;

  @Comment(desc = "所属订单编号")
  private Long orderNo;

  @Comment(desc = "交易状态,0:取消 1:正常 2:换货 3:退货")
  private ItemStatus itemStatus = ItemStatus.NORMAL;

  @Comment(desc = "享受的活动")
  private Set<Activity> activities = Sets.newHashSet();

  @Comment(desc = "添加一个活动")
  @JsonIgnore
  public synchronized boolean addActivity(@NonNull final Activity activity) {
    boolean isOk = Stream.of(activity.getActivityNo(), activity.getName())
        .map(Objects::nonNull).reduce(true, Boolean::logicalAnd);
    if (!isOk) {
      throw new IllegalArgumentException("活动编号和名称必须有");
    }
    isOk = this.activities.add(activity);
    if (isOk) {
      activity.setTargetCode(this.itemCode);
    }
    return isOk;
  }

  @Comment(desc = "移除一个活动")
  @JsonIgnore
  public synchronized boolean removeActivity(@NonNull final Activity activity) {
    return this.activities.remove(activity);
  }

}
