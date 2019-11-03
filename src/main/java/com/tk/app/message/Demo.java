package com.tk.app.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tank198435163.com
 */
@Data
public class Demo implements Serializable {

  private String username;

  private String job;

  private String idCard;
}
