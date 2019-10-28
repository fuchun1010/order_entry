package com.tk.app.common.ds;

import com.tk.app.common.Constants;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.MapOptions;
import org.redisson.api.RBucketRx;
import org.redisson.api.RMapRx;
import org.redisson.api.RedissonRxClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.testng.collections.Maps;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author tank198435163.com
 */
public class RedisTest {

  @Test
  public void test1() {
    String name = "demo";
    boolean rs = this.handleRedisKey(name, client -> {
      RBucketRx<String> v = client.getBucket(name);
      return v.trySet("hello").blockingGet();
    });
    System.out.println(rs);
  }

  @Test
  public void test2() {
    String name = "demo";
    boolean rs = this.handleRedisKey(name, client -> {
      RMapRx<String, String> hash = client.getMap(name);
      Map<String, String> values = Maps.newHashMap();
      values.putIfAbsent("orderNo", "s0001");
      values.put("name", "bruce");
      hash.expire(60, TimeUnit.SECONDS).blockingGet();
      return hash.putAll(values).toSingle(() -> true).blockingGet();
    });
    System.out.println(rs);
  }

  @Test
  public void test3() {
    String name = "redission";
    Optional<String> opt = this.handleRedisKey(name, client -> {
      RMapRx<String, String> hash = client.getMap(name);

      return Optional.ofNullable(hash.get("a").blockingGet());
    });
    System.out.println(opt.orElse(Constants.EMPTY_STR));
  }


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
    config.setCodec(StringCodec.INSTANCE);
    config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(3);
    RedissonRxClient client = Redisson.createRx(config);
    T rs = fun.apply(client);
    return rs;
  }


  private Config config;
}
