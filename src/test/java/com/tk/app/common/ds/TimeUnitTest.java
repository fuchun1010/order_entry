package com.tk.app.common.ds;


import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.testng.collections.Lists;

import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeUnitTest {

  @Test
  public void testMillions() {
    long rs = TimeUnit.SECONDS.toMillis(120);
    System.out.println(rs);
  }

  @Test
  @SneakyThrows
  public void testUniqueStr() {
    MessageDigest md = MessageDigest.getInstance("MD5");

    List<String> items = Lists.newArrayList(32);

    items.add("110032");
    items.add("100615");
    items.add("100614");
    items.add("113411");
    items.add("105795");
    items.add("106712");
    items.add("112291");
    items.add("199712");
    items.add("101011");

    items.stream().forEach(item -> {
      byte[] data = md.digest(item.getBytes());
      String digest = Hex.encodeHexString(data);
      System.out.println(String.format("raw data: %s, len: %d, prefix: %s", digest, digest.length(), digest.substring(0, 7)));
    });


  }

}
