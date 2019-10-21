package com.tk.app.common.ds;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author tank198435163.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DataSourceCfgTest {

  @Test
  public void orderPublicDataSource() {
    Assert.assertTrue(Objects.nonNull(this.defaultDataSource));
  }

  @Test
  public void orderInd0DataSource() {
  }

  @Test
  public void orderInd1DataSource() {
  }

  @Test
  public void orderInd2DataSource() {
  }

  @Test
  public void orderInd3DataSource() {
  }

  @Autowired
  @Qualifier("publicDs")
  private DataSource defaultDataSource;
}