package com.tk.app.common.interceptor;

import com.tk.app.common.Comment;
import com.tk.app.common.Constants;
import com.tk.app.common.holder.DbHolder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tank198435163.com
 */
@Slf4j
@Aspect
@Component
@Comment(desc = "split database and table with DbSelector annotation")
public class DbSplitInterceptor {

  @Before("@annotation(DbSelector)")
  public void selectDbAndTableWithOrderId(final JoinPoint joinPoint) {
    MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
    boolean shouldSplitDbAndTable = methodSignature.getMethod().isAnnotationPresent(DbSelector.class);
    if (!shouldSplitDbAndTable) {
      return;
    }

    boolean isContained = false;

    Object[] values = joinPoint.getArgs();
    if (values.length == 0) {
      throw new IllegalArgumentException("no data passed");
    }

    String orderId = this.<String>fetchSplitFieldValue(joinPoint).orElse(Constants.EMPTY_STR);
    isContained = !orderId.equalsIgnoreCase(Constants.EMPTY_STR);
    if (!isContained) {
      throw new IllegalArgumentException("parameter orderId missed");
    }

    int dbIndex = selectIndex(Integer.parseInt(orderId), 4);
    int tableIndex = selectIndex(Integer.parseInt(orderId), 128);
    DbHolder.determineDb(dbIndex);
    DbHolder.determineTable(tableIndex);

  }

  @After("@annotation(DbSelector)")
  public void cleanThreadInfo() {
    DbHolder.clean();
  }

  @Comment(desc = "获取分库分表的字段值")
  @SuppressWarnings("unchecked")
  private <T> Optional<T> fetchSplitFieldValue(final JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
    int index = -1;
    //TODO continue
    boolean existed = false;
    for (String parameterName : methodSignature.getParameterNames()) {
      if ("orderId".equalsIgnoreCase(parameterName)) {
        existed = true;
        break;
      }
      index++;
    }

    if (existed) {
      return (Optional.ofNullable(((T) args[index])));
    } else {
      index = 0;
      Method method = null;
      Class<?>[] types = methodSignature.getParameterTypes();
      List<String> targetMethods = targetFilterFieldNames();
      for (Class<?> clazzType : types) {
        for (Method declaredMethod : clazzType.getDeclaredMethods()) {
          if (targetMethods.contains(declaredMethod.getName())) {
            method = declaredMethod;
            existed = true;
            break;
          }
        }
        if (existed) {
          try {
            val data = method.invoke(args[index], new Object[]{});
            return Optional.of(((T) data));
          } catch (Exception e) {
            e.printStackTrace();
          }

        }
        index++;

      }

    }


    return Optional.empty();
  }

  private List<String> targetFilterFieldNames() {
    return Stream.of("getOrderId", "getOrderNo", "getOrderNum").collect(Collectors.toList());
  }

  private int selectIndex(int orderId, int factor) {
    return orderId & (factor - 1);
  }

}
