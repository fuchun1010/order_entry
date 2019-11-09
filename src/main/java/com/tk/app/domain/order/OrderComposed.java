package com.tk.app.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tk.app.common.Comment;
import lombok.Getter;
import org.testng.collections.Sets;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tank198435163.com
 */
@Comment(desc = "订单构成")
public abstract class OrderComposed {

  /**
   * 最大消费者限制
   *
   * @return
   */
  @JsonIgnore
  public abstract Integer limitConsumers();

  @JsonIgnore
  public AtomicInteger counter = new AtomicInteger(0);

  /**
   * 添加一个订单
   *
   * @param order
   * @return
   */
  @JsonIgnore
  public boolean addOrder(Long order) {
    synchronized (orders) {
      orders.add(order);
      return this.isEnough();
    }
  }

  @JsonIgnore
  public Set<Long> participates() {
    return orders;
  }

  @JsonIgnore
  private boolean isEnough() {
    return this.limitConsumers().compareTo(this.counter.incrementAndGet()) == 0;
  }

  private Set<Long> orders = Sets.newHashSet();

  @Getter
  private String captain;
}
