package com.tk.app;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tank198435163.com
 */
@Data
public class OrderConfirmHeaderDTO implements Serializable {

  private String channel;

  private String model;

  private String os;
}
