package com.tk.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import org.testng.collections.Lists;

import java.util.List;

/**
 * @author tank198435163.com
 */
@Data
public class Address {

  private String orderNo;

  private String address;

  private String tableName;

  private List<String> fields = Lists.newArrayList();

  @JsonIgnore
  public Address addField(@NonNull final String field) {
    if (!fields.contains(field)) {
      fields.add(field);
    }
    return this;
  }
}
