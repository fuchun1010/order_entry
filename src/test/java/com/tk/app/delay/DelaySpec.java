package com.tk.app.delay;

import com.tk.app.common.Comment;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
@Comment(desc = "处理redis过期队列")
public class DelaySpec {

  @Test
  @SneakyThrows
  @Comment(desc = "添加一个确认订单,score是距离下一个15分钟的时间")
  public void addWaitExpiredOrders() {
    long differ = expiredMinutes();

    RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(confirmedOrders);
    sortedSet.add(differ, "s0003");
    TimeUnit.SECONDS.sleep(10);
    differ = expiredMinutes();
    sortedSet.add(differ, "s0004");
  }

  @Test
  public void findExpiredOrders() {
    long differ = this.beforeFifteenMinutes();
    RScoredSortedSet<String> sortedSet = this.redissonClient.getScoredSortedSet(confirmedOrders);
    int size = sortedSet.size();
    sortedSet.entryRange(0, false, differ, true, 0, size)
        .stream().forEach(element -> {
      System.out.println(String.format("value:%s, score:%f", element.getValue(), element.getScore()
      ));
    });

  }

  @Test
  public void findExpiredOrders2() {
    this.redissonClient.getScoredSortedSet(confirmedOrders)
        .entryRange(1, true, 2, true, 0, 2)
        .stream().map(ScoredEntry::getValue)
        .forEach(System.out::println);
  }

  @Test
  public void removeSpecialItem() {
    this.redissonClient.getScoredSortedSet(confirmedOrders).remove("s0002");
  }

  @Test
  public void testBeforeFifteenMinutes() {
    long rs = this.beforeFifteenMinutes();
    System.out.println(rs);
  }

  private long beforeFifteenMinutes() {
    LocalDateTime beforeFifteenEnd = LocalDateTime.now().minusMinutes(15);
    long dateTimePoint = beforeFifteenEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    return TimeUnit.MILLISECONDS.toMinutes(dateTimePoint);
  }

  private long expiredMinutes() {
    LocalDateTime nextFifteenEnd = LocalDateTime.now().plusMinutes(15);
    long expiredTime = nextFifteenEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    return TimeUnit.MILLISECONDS.toMinutes(expiredTime);
  }


  private String convertFor(@NonNull final LocalDateTime dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return formatter.format(dateTime);
  }

  String confirmedOrders = "waitingExpiredOrders";

  @Autowired
  private RedissonClient redissonClient;

}
