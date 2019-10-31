package com.tk.app.common.ds;

import lombok.NonNull;
import org.junit.Test;
import org.testng.collections.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class OrderTest {

  @Test
  public void testSplitOrder() {
    Item item_01 = new Item().setItemCode("100041").setDesc("apple_01").setRepository("A");
    Item item_02 = new Item().setItemCode("100042").setDesc("apple_02").setRepository("B");
    Item item_03 = new Item().setItemCode("100043").setDesc("apple_03").setRepository("B");

    Order primaryOrder = new Order();
    primaryOrder.setOrderNo(String.valueOf(this.id.incrementAndGet()));
    primaryOrder.setFullPrice("33.6");
    primaryOrder.setChannel("mt");
    primaryOrder.setDisCounted("30.6");

    primaryOrder.addItem(item_01);
    primaryOrder.addItem(item_02);
    primaryOrder.addItem(item_03);


    List<Order> orders = this.splitOrder(primaryOrder);
    System.out.println(orders);
  }

  private List<Order> splitOrder(final Order primaryOrder) {
    List<Order> rs = Lists.newArrayList(32);
    this.splitOrder(primaryOrder, rs);
    return rs;
  }

  private void splitOrder(@NonNull final Order primaryOrder, @NonNull final List<Order> orders) {
    Order order = new Order();
    order.setOrderNo(String.valueOf(this.id.incrementAndGet()));
    for (Item item : primaryOrder.items) {
      boolean isOk = false;
      if (isOk) {
        //TODO 没有调试
        this.splitOrder(order, orders);
      } else {
        order.addItem(item);
      }
    }
    orders.add(order);

  }

  private AtomicLong id = new AtomicLong(0L);
}
