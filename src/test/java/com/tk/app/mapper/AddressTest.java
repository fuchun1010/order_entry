package com.tk.app.mapper;

import com.tk.app.domain.Address;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressTest {

  @Test
  public void find() {
    int rs = this.address.find();
    Assert.assertTrue(new Integer(rs).compareTo(1) == 0);
  }


  @Test
  public void testAdd() {
    Address receiverAdd = new Address();
    receiverAdd.setOrderNo(String.valueOf(1234567));
    receiverAdd.setAddress("深圳市南山区留创");

    this.address.add(receiverAdd);
  }

  @Autowired
  private IAddress address;
}