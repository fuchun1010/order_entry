package com.tk.app.domain.order;

import com.annimon.stream.Stream;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tk.app.common.Comment;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.testng.collections.Sets;

import java.util.Objects;
import java.util.Set;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "订单渠道")
public class Channel {

  @SneakyThrows
  @JsonIgnore
  public synchronized boolean addActivity(@NonNull final Activity activity) {

    return CollectionAction.addElement(this.activities,
        String.valueOf(orderNo),
        activity,
        element -> Stream.of(element.getActivityNo(),
            element.getName(), element.getTargetCode())
            .map(Objects::nonNull)
            .reduce(true, Boolean::logicalAnd),
        "活动中的ActivityNo和targetCode还有名称是必须填写的");

  }

  public synchronized boolean removeActivity(@NonNull final Activity activity) {
    return this.activities.remove(activity);
  }

  private String entry;

  private Long orderNo;

  private Set<Activity> activities = Sets.newHashSet();


}
