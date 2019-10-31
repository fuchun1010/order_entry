package com.tk.app.common.ds;

import org.junit.Test;
import org.testng.collections.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class OrderTest {

  @Test
  public void testSplitOrder() {
    Item item_01 = new Item().setItemCode("100041").setDesc("apple_01").setRepository("A").setDispatchDate("2019-12-11");
    Item item_02 = new Item().setItemCode("100042").setDesc("apple_02").setRepository("B").setDispatchDate("2019-10-31");
    Item item_03 = new Item().setItemCode("100043").setDesc("apple_03").setRepository("B").setDispatchDate("2019-10-31");
    Item item_04 = new Item().setItemCode("100044").setDesc("apple_04").setRepository("B").setDispatchDate("2019-11-11");

    Order primaryOrder = new Order();
    primaryOrder.setOrderNo(String.valueOf(this.id.incrementAndGet()));
    primaryOrder.setFullPrice("33.6");
    primaryOrder.setChannel("mt");
    primaryOrder.setDisCounted("30.6");

    primaryOrder.addItem(item_01);
    primaryOrder.addItem(item_02);
    primaryOrder.addItem(item_03);
    primaryOrder.addItem(item_04);

    List<Order> orders = this.splitOrder(primaryOrder).values().stream().collect(Collectors.toList());
    System.out.println(orders.size());
  }

  private Map<Long, Order> splitOrder(final Order primaryOrder) {
    Map<Long, Order> rs = Maps.newHashMap();
    Order order = new Order();
    for (Item item : primaryOrder.getItems()) {
      if (rs.isEmpty()) {
        Long id = this.id.incrementAndGet();
        order.setOrderNo(String.valueOf(id));
        order.addItem(item);
        rs.put(id, order);
      } else {
        Collection<Order> orders = rs.values();
        Optional<Order> targetOpt = this.needSplitUpIfNecessary(orders, item,
            this::isEqualGroup,
            this::isEqualDispatch);
        if (targetOpt.isPresent()) {
          targetOpt.get().addItem(item);
        } else {
          Long id = this.id.incrementAndGet();
          order = new Order();
          order.setOrderNo(String.valueOf(id));
          order.addItem(item);
          rs.put(id, order);
        }

      }
    }
    return rs;
  }

  private Optional<Order> needSplitUpIfNecessary(Collection<Order> orders, Item item, BiFunction<Collection<Order>, Item, Optional<Order>>... functions) {

    boolean isOk = true;
    Optional<Order> targetOpt = Optional.empty();
    for (BiFunction<Collection<Order>, Item, Optional<Order>> fun : functions) {
      targetOpt = fun.apply(orders, item);
      isOk &= targetOpt.isPresent();
      if (!isOk) {
        break;
      }
    }

    return !isOk ? Optional.empty() : targetOpt;
  }

  private Optional<Order> isEqualGroup(final Collection<Order> orders, final Item item) {

    return this.isEqualLogical(orders, item, (order, newItem) -> order.getItems()
        .stream()
        .map(Item::getRepository).filter(item.getRepository()::equals).count() > 0
    );
  }

  private Optional<Order> isEqualDispatch(final Collection<Order> orders, final Item item) {
    return this.isEqualLogical(
        orders,
        item,
        (order, newItem) -> order.getItems().stream().map(Item::getDispatchDate).filter(item.getDispatchDate()::equals).count() > 0
    );
  }

  private Optional<Order> isEqualLogical(final Collection<Order> orders, final Item item, BiFunction<Order, Item, Boolean> logical) {
    for (Order order : orders) {
      boolean isOk = logical.apply(order, item);
      if (isOk) {
        return Optional.of(order);
      }
    }

    return Optional.empty();
  }

  private AtomicLong id = new AtomicLong(0L);
}
