package com.tk.app.domain.order;

import com.tk.app.common.Comment;

/**
 * @author tank198435163.com
 */
@Comment(desc = "销售类型")
public class SalesType {

  @Comment(desc = "销售类型: 100是正常， 200是配送, 300周期购")
  private Short type;

}
