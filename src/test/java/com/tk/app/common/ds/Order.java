package com.tk.app.common.ds;

import lombok.Data;

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
}
