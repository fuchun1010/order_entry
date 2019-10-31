package com.tk.app.common.ds;

import lombok.Data;
import lombok.NonNull;
import org.testng.collections.Sets;

import java.util.Objects;
import java.util.Set;

/**
 * "orderNo"
 * 2) "storeCode"
 * 3) "fullPrice"
 * 4) "disCounted"
 * 5) "subOrders"
 * 6) "channel"
 * 7) "salesType"
 * 8) "participants"
 * 9) "createDate"
 * 10) "storeName"
 */

@Data
public class Order {
  String orderNo;
  String storeCode;
  String fullPrice;
  String disCounted;
  String subOrders;
  String channel;
  String salesType;
  String createDate;
  String storeName;

  Set<Item> items;

  public void addItem(@NonNull final Item item) {
    if (Objects.isNull(items)) {
      items = Sets.newHashSet();
    }
    items.add(item);
  }
}
