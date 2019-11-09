package com.tk.app.domain.order;

import com.tk.app.common.Comment;
import lombok.Getter;

/**
 * @author tank198435163.com
 */
@Comment(desc = "组团渠道,目前只有微信")
@Getter
public abstract class GroupChannel {

  private Short type;
}
