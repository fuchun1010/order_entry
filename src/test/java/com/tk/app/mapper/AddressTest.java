package com.tk.app.mapper;

import com.tk.app.common.interceptor.DbSelector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressTest {

  @Test
  @DbSelector
  public void find() {
    this.address.find();
  }

  @Autowired
  private Address address;
}