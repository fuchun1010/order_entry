package com.tk.app.domain.order;

import com.annimon.stream.Stream;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tk.app.common.Comment;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.testng.collections.Sets;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author tank198435163.com
 */
@Data
@Comment(desc = "jvm交易订单,不管是什么类型，任何渠道的订单最后都转为这一个对象结构")
public class TradeOrder {

  @Comment(desc = "订单渠道,可是美团,pos,app, app plus,心享")
  private Channel channel;

  @Comment(desc = "订单编号")
  private Long orderNo;

  @Comment(desc = "品牌")
  private OrderBrand brand;

  @Comment(desc = "订单类型")
  private OrderType orderType;

  @Comment(desc = "创建日期")
  private String createDate;

  @Comment(desc = "顾客id")
  private String customerId;

  @Comment(desc = "全价,这个地方用string是为了保持精度")
  private String fullPrice;

  @Comment(desc = "折扣价")
  private String disCountPrice;

  @Comment(desc = "订单支付,可能是多笔支付")
  private Set<Payment> payments = Sets.newHashSet();

  @Comment(desc = "订单的果品")
  private Set<Item> items = Sets.newHashSet();

  @Comment(desc = "订单活动")
  private Set<Activity> activities = Sets.newHashSet();

  @Comment(desc = "收货地址")
  private ReceiverAddress receiverAddress;

  @Comment(desc = "是否需要开具发票")
  private RequiredInvoice requiredInvoice;

  @Comment(desc = "订单备注")
  private String remark;

  @Comment(desc = "配送开始时间,原始单据不要,拆单以后需要这个属性")
  private String dispatchStartDate = "2018-06-01";

  @Comment(desc = "配送结束时间,原始单据不要,拆单以后需要这个属性")
  private String dispatchEndDate = "2018-06-01";

  @Comment(desc = "单据是否团购")
  private OrderComposed orderComposed = new NormalOrderComposed();

  @Comment(desc = "骑行配送距离（订单类型为自提单时，配送距离为0，app为必填项，小程序拼团非必填）")
  private Optional<String> dispatchDistance = Optional.empty();

  @SneakyThrows
  public synchronized boolean addActivity(@NonNull final Activity activity) {
    return CollectionAction.addElement(
        this.activities,
        String.valueOf(this.orderNo),
        activity,
        element -> Stream.of(
            element.getActivityNo(),
            element.getTargetCode())
            .map(Objects::nonNull)
            .reduce(true, Boolean::logicalAnd), "活动对象的targetCode和activityNo都不能是null");
  }

  @JsonIgnore
  @Comment(desc = "添加活动")
  @SneakyThrows
  public synchronized boolean addItem(@NonNull final Item item) {
    return CollectionAction.addElement(
        this.items,
        this.orderNo,
        item,
        elements -> Stream.of(item.getItemCode(),
            item.getDisCount(),
            item.getName(),
            item.getUnitPrice(),
            item.getWeight())
            .map(Objects::nonNull)
            .reduce(true, Boolean::logicalAnd), "果品对象的orderNo,paymentNo,money字段不能是null");
  }

  @JsonIgnore
  @Comment(desc = "添加支付")
  @SneakyThrows
  public synchronized boolean addPayment(@NonNull final Payment payment) {

    boolean isOk = CollectionAction.addElement(this.payments, this.orderNo, payment, element -> Stream.of(payment.getOrderNo(),
        payment.getPayDateTime(),
        payment.getPaymentNo(),
        payment.getMoney())
        .map(Objects::nonNull)
        .reduce(true, Boolean::logicalAnd), "支付对象的orderNo,paymentNo,money字段不能是null");
    return isOk;
  }

  @JsonIgnore
  public synchronized void removePayment(@NonNull final Payment payment) {
    if (payment.getOrderNo() == null) {
      throw new IllegalArgumentException("支付记录的订单号不允许是空");
    }
    this.payments.remove(payment);
  }


}
