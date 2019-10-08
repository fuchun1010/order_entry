package com.tk.app.message.order.request;

import com.tk.app.common.Comment;
import com.tk.app.message.OrderReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "pos机下单请求")
@EqualsAndHashCode(callSuper = false)
public class PosOrderReq extends OrderReq {

  @Comment(desc = "门店编号")
  private String storeCode;
}
