package com.tk.app.domain.order;

import com.annimon.stream.Stream;
import com.google.common.collect.Maps;
import lombok.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author tank198435163.com
 */
public class CollectionAction {


  public static <I, T> boolean addElement(@NonNull final Collection<I> collection,
                                          @NonNull final T targetCode,
                                          @NonNull final I element, Predicate<I> predicate, String errorMsg) throws InvocationTargetException, IllegalAccessException {

    boolean isOk = predicate.test(element);

    if (!isOk) {
      throw new IllegalArgumentException(errorMsg);
    }

    isOk = collection.add(element);

    if (isOk) {
      Method method = Stream.of(element.getClass().getDeclaredMethods()).filter(md ->
          "setOrderNo".equalsIgnoreCase(md.getName()) || "setTargetCode".equalsIgnoreCase(md.getName()))
          .findFirst().get();

      if (method != null) {
        method.invoke(element, targetCode);
      }
    }

    return isOk;
  }

  public static Map<String, String> toKv(@NonNull final Object target, @NonNull final Predicate predicate) throws Exception {

    Map<String, String> value = Maps.newHashMap();

    List<Method> methods = Stream.of(target.getClass().getDeclaredMethods())
        .filter(method -> method.getName().indexOf("get") != -1)
        .toList();

    for (Method method : methods) {
      String methodName = method.getName().toLowerCase();
      String fieldName = methodName.substring(3, methodName.length());
      Object data = method.invoke(target, new Object[]{});
      if (predicate.test(data)) {
        value.putIfAbsent(fieldName, String.valueOf(data));
      }

    }

    return value;
  }
}
