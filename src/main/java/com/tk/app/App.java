package com.tk.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/**
 * com.tk.app.mapper扫描更多的包，保持持久化接口存放位置灵活性
 *
 * @author tank198435163.com
 * 订单域入口
 */
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@MapperScan("com.tk.app")
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
