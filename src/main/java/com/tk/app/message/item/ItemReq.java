package com.tk.app.message.item;

import lombok.Data;

import java.util.Objects;

/**
 * @author tank198435163.com
 */
@Data
public class ItemReq {

  private String itemCode;

  private String itemName;

  private Double quality;

  private Double unitPrice;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ItemReq)) {
      return false;
    }
    ItemReq itemReq = (ItemReq) o;
    return Objects.equals(getItemCode(), itemReq.getItemCode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getItemCode());
  }
}
