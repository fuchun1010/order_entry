package com.tk.app.common.interceptor;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * @author tank198435163.com
 */
@Aspect
@Slf4j
@Component
public class CostInterceptor {

  @Around("execution(public * com.tk.app.controller.**.**(..))")
  public <T> ResponseEntity<T> calculateCost(@NonNull final ProceedingJoinPoint joinPoint) throws Throwable {
    val start = Instant.now();
    Instant end = null;
    ResponseEntity<T> rs = null;
    try {
      rs = ((ResponseEntity) joinPoint.proceed());
    } finally {
      end = Instant.now();
    }
    val signature = joinPoint.getSignature();
    val controllerName = signature.getDeclaringType().getName();
    val method = joinPoint.getSignature().getName();
    long costTime = Duration.between(start, end).toMillis();
    val maxAllowedTimeConsume = Long.parseLong(this.environment.getProperty("orderDomain.maxAllowedTimeConsume"));
    if (costTime >= maxAllowedTimeConsume) {
      log.warn("controller :[{}], method:[{}],cost [{}] 毫秒", controllerName, method, costTime);
    } else {
      log.info("controller :[{}], method:[{}],cost [{}] 毫秒", controllerName, method, costTime);
    }
    return rs;
  }

  @Autowired
  private Environment environment;

}
