package com.tk.app.common.ds;

import com.tk.app.common.holder.DbHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author tank198435163.com
 */
public class OrderSourceRouter extends AbstractRoutingDataSource {
  @Override
  protected Object determineCurrentLookupKey() {
    return DbHolder.fetchSelectedDb();
  }
}
