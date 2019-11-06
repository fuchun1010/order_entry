package com.tk.app.mapper;

import com.tk.app.common.interceptor.DbSelector;
import com.tk.app.domain.Address;
import lombok.NonNull;

/**
 * @author tank198435163.com
 */
public interface IAddress {
  /**
   * only test
   *
   * @return
   */
  int find();

  /**
   * 添加一条记录
   *
   * @param address
   * @return
   */
  @DbSelector
  int add(@NonNull final Address address);
}
