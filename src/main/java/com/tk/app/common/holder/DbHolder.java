package com.tk.app.common.holder;

import lombok.NonNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author tank198435163.com
 */
public class DbHolder {

  public static void determineDb(@NonNull final String dataSource) {
    dbSelector.set(dataSource);
  }

  public static Optional<String> fetchSelectedDb() {
    return Optional.ofNullable(dbSelector.get());
  }

  public static void determineTable(@NonNull final Integer tableIndex) {
    String tableSuffix = String.valueOf(tableIndex);
    StringBuilder strBuilder = new StringBuilder("tab_order_");
    String strZero = IntStream.range(0, 3 - tableSuffix.length()).boxed().map(i -> "0").collect(Collectors.joining());
    strBuilder.append(strZero);
    strBuilder.append(tableSuffix);
    tableSelector.set(strBuilder.toString());
  }

  public static Optional<String> fetchSelectedTable() {
    return Optional.ofNullable(tableSelector.get());
  }

  public static void clean() {
    dbSelector.remove();
    tableSelector.remove();
  }

  private static final ThreadLocal<String> tableSelector = new ThreadLocal<>();

  private static final ThreadLocal<String> dbSelector = new ThreadLocal<>();
}
