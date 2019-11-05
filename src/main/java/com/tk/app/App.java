package com.tk.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/**
 * @author tank198435163.com
 * 订单域入口
 */
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@MapperScan("com.tk.app.mapper")
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
