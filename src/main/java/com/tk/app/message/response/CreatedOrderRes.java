package com.tk.app.message.response;

import com.tk.app.common.Comment;
import lombok.Data;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "订单创建完成返回对象")
public class CreatedOrderRes {

  private Long orderNo;
}
