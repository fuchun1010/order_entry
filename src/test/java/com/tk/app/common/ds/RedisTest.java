package com.tk.app.common.ds;

import com.tk.app.common.Constants;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.MapOptions;
import org.redisson.api.RedissonRxClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.function.Function;

/**
 * @author tank198435163.com
 */
public class RedisTest {

  @Test
  public void readHashValue() {
    String key = "order:s0001";
    Config config = new Config();
    config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(3);
    try {
      RedissonRxClient client = Redisson.createRx(config);
      String value = client.getMap(key, StringCodec.INSTANCE).get("orderNo").cast(String.class).blockingGet(Constants.EMPTY_STR);
      System.out.println(value);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void writeHashValue() {
    String name = "redission";
    String rs = (String) this.handleRedisKey(name, client ->
        client.getMap(name, StringCodec.INSTANCE,
            MapOptions.defaults().writeMode(MapOptions.WriteMode.WRITE_THROUGH))
            .putIfAbsent("a", "hello")
            .blockingGet("OK"));
    System.out.println(rs);

  }

  private <T> T handleRedisKey(String key, Function<RedissonRxClient, T> fun) {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(3);
    RedissonRxClient client = Redisson.createRx(config);
    T rs = fun.apply(client);
    return rs;
  }


  private Config config;
}
