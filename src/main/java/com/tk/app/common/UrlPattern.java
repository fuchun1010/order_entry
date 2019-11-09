package com.tk.app.common;

/**
 * @author tank198435163.com
 */
public interface UrlPattern {

  String URL_PREFIX = "/order_domain/v1";

  String ORDER_CREATED = "/order/created";

  String ORDER_QUERY_BY_OrderNo = "/{orderNo}/order";

  String MOCK_EXP = "/order/exp";

  String WELCOME = "/order/welcome";

  String MOCK_ORDER_CREATED = "/order/mock/order/created";

  String TRADE_CREATED = "/order/mock/trade/created";

  String CONFIRM_ORDER = "/order/mock/confirm";

}
