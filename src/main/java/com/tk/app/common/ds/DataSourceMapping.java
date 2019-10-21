package com.tk.app.common.ds;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author tank198435163.com
 */
public class DataSourceMapping {


  @Bean("globalDataSource")
  public Map<Object, Object> registerDataSource() {
    Map<Object, Object> maps = Maps.newHashMap();
    maps.putIfAbsent("default", this.publicDs);
    maps.putIfAbsent("orderDs0", this.orderDs0);
    maps.putIfAbsent("orderDs1", this.orderDs1);
    maps.putIfAbsent("orderDs2", this.orderDs2);
    maps.putIfAbsent("orderDs3", this.orderDs3);
    return maps;
  }


  @Autowired
  @Qualifier("publicDs")
  private DataSource publicDs;

  @Autowired
  @Qualifier("orderDs0")
  private DataSource orderDs0;


  @Autowired
  @Qualifier("orderDs1")
  private DataSource orderDs1;

  @Autowired
  @Qualifier("orderDs2")
  private DataSource orderDs2;

  @Autowired
  @Qualifier("orderDs3")
  private DataSource orderDs3;

}
