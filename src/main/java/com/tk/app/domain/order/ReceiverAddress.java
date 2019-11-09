package com.tk.app.domain.order;

import com.tk.app.common.Comment;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "收货人地址")
public class ReceiverAddress {

  public boolean isValidation() {
    return !StringUtils.isEmpty(this.mobile);
  }

  @Comment(desc = "所属订单编号")
  private String orderNo;

  @Comment(desc = "收货地址")
  private String address;

  @Comment(desc = "手机号码")
  private String mobile;

  @Comment(desc = "邮编")
  private String postCode;

  @Comment(desc = "昵称")
  private String nickName;

  @Comment(desc = "收货地址（自提单为门店ID")
  private Integer receiveAddrID;

}
