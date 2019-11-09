package com.tk.app.domain.order;

import com.tk.app.common.Comment;
import lombok.Data;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "微信渠道")
public class WeChatGroup extends GroupChannel {

  @Comment(desc = "微信头像URL")
  private String wxProfileUrl;

  @Comment(desc = "wxUnionID")
  private String wxOpenID;

  @Comment(desc = "微信昵称")
  private String wxNickName;

  @Comment(desc = "微信unionID(小程序拼团必填)")
  private String wxUnionID;
  
}
