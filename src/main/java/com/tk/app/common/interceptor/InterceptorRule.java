package com.tk.app.common.interceptor;

/**
 * @author tank198435163.com
 */
public interface InterceptorRule {

  String CONTROLLER_INTERCEPTOR_RULE = "execution(public * com.tk.app.controller.**.**(..))";
}
