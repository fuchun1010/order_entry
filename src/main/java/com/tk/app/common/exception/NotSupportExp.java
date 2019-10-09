package com.tk.app.common.exception;

import com.tk.app.common.Comment;
import lombok.NonNull;

/**
 * @author tank198435163.com
 */
@Comment(desc = "实现不支持的操作")
public class NotSupportExp extends Exception {
  public NotSupportExp() {
    super();
  }

  public NotSupportExp(@NonNull final String msg) {
    super(msg);
  }
}
