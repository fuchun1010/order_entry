package com.tk.app.domain.order;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Objects;

public class ReceiverAddressTest {

  @Test
  @SneakyThrows
  public void toKv() {

    Map<String, String> rs = CollectionAction.toKv(this.receiverAddress, Objects::nonNull);

    for (Map.Entry<String, String> entry : rs.entrySet()) {
      System.out.println(String.format("field:%s ---> value: %s", entry.getKey(), entry.getValue()));
    }
  }

  @Before
  public void init() {
    this.receiverAddress = new ReceiverAddress();
    this.receiverAddress.setAddress("bj");
    this.receiverAddress.setMobile("18623377391");
  }

  private ReceiverAddress receiverAddress;
}