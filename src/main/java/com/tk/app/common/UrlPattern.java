package com.tk.app.common;

/**
 * @author tank198435163.com
 */
public interface UrlPattern {

  String URL_PREFIX = "/order_domain/v1";

  String ORDER_CREATED = "/order/created";

  String ORDER_QUERY_BY_OrderNo = "/{orderNo}/order";
}
