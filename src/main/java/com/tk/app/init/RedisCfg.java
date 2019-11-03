package com.tk.app.init;

import com.tk.app.common.Comment;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author tank198435163.com
 */
@Slf4j
@Configuration
public class RedisCfg {


  @Bean
  @Comment(desc = "分布式锁定实现过期key消费")
  public CommandLineRunner initRunner() {

    return (args) -> {
      String key = "__keyevent@3__:expired";
      String name = this.environment.getProperty("server.name");
      redisClient.getTopic(key).addListener(String.class, (channel, msg) -> {
        RLock lock = redisClient.getLock(name);
        boolean fetched = false;
        try {
          fetched = lock.tryLock(50, TimeUnit.SECONDS.toMillis(120), TimeUnit.MILLISECONDS);
          System.out.println("-----");
          if (fetched) {
            System.out.println(String.format("expired key:%s", msg));
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          if (fetched) {
            log.info("server:[{}] release lock");
            lock.unlock();
          }
        }

      });
      this.startOrderTopicConsumer();

    };
  }


  @Bean
  public RedissonClient initRedis() {
    Config config = new Config();
    config.setCodec(StringCodec.INSTANCE);
    config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(3);
    RedissonClient client = Redisson.create(config);
    return client;
  }

  @Bean
  public DefaultMQProducer initProducer() {
    String group = this.mqConfig.getGroup();
    DefaultMQProducer producer = new DefaultMQProducer(group);
    producer.setNamesrvAddr(this.mqConfig.getNameSvr());
    try {
      producer.start();
      log.info("rocketMq发送端启动成功");
    } catch (MQClientException e) {
      log.error("rocket mq producer init error:[{}]", e.getErrorMessage());
    }

    return producer;

  }

  @Bean
  public DefaultMQPushConsumer initConsumer() {
    String consumerGroup = this.mqConfig.getConsumerGroup();
    String nameSvr = this.mqConfig.getNameSvr();
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
    consumer.setNamesrvAddr(nameSvr);
    try {
      consumer.subscribe("orderTopic", "*");
    } catch (MQClientException e) {
      e.printStackTrace();
    }
    return consumer;

  }

  private void startOrderTopicConsumer() {
    this.orderConsumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (list.size() > 0) {
          MessageExt msg = list.get(0);
          String body = new String(msg.getBody());
          log.info("order topic consumer 收到消息了, tag:[{}] 消息体:[{}]", msg.getTags(), body);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    try {
      this.orderConsumer.start();
      log.info("order topic 消费者启动成功,开始监听");
    } catch (MQClientException e) {
      log.error("order topic consumer error:[{}]", e.getErrorMessage());
    }
  }

  @Autowired
  private RedissonClient redisClient;

  @Autowired
  private Environment environment;

  @Autowired
  private MqConfig mqConfig;

  @Autowired
  private DefaultMQPushConsumer orderConsumer;

}
