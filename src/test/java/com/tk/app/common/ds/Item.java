package com.tk.app.common.ds;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  private String itemCode;

  private String repository;

  private String desc;

  private String dispatchDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Item)) return false;
    Item item = (Item) o;
    return Objects.equal(getItemCode(), item.getItemCode());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getItemCode());
  }
}
