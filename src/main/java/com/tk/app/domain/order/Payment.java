package com.tk.app.domain.order;

import com.google.common.base.Objects;
import com.tk.app.common.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tank198435163.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Comment(desc = "支付记录")
public class Payment {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Payment)) {
      return false;
    }

    if (this.orderNo == null) {
      return false;
    }

    if (this.paymentNo == null) {
      return false;
    }

    if (this.payDateTime == null) {
      return false;
    }

    Payment payment = (Payment) o;
    return this.orderNo.compareTo(payment.orderNo) == 0 &&
        Objects.equal(getPaymentNo(), payment.getPaymentNo());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getOrderNo(), getPaymentNo());
  }

  @Comment(desc = "订单编号")
  private Long orderNo;

  @Comment(desc = "支付编号")
  private String paymentNo;

  @Comment(desc = "支付时间")
  private String payDateTime;

  @Comment(desc = "支付金额,这个地方用string=>double=>BigDecimal")
  private String money;

  @Comment(desc = "支付类别")
  private String type;


}
