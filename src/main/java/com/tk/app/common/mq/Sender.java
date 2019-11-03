package com.tk.app.common.mq;

import com.tk.app.common.Comment;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tank198435163.com
 */
@Slf4j
@Comment
@Component
public class Sender {

  public void sendSyncWithoutTags(@NonNull final String topicName, @NonNull String jsonBody) {
    Message msg = new Message(topicName, jsonBody.getBytes());
    this.sendSync(msg);
  }

  public void sendSyncWithTag(@NonNull final String topicName,
                              @NonNull final String tag,
                              @NonNull String jsonBody) {
    Message msg = new Message(topicName, tag, jsonBody.getBytes());
    this.sendSync(msg);
  }

  private void sendSync(@NonNull final Message msg) {
    try {
      this.producer.send(msg);
    } catch (MQClientException | MQBrokerException | RemotingException | InterruptedException e) {

    }
  }

  @Autowired
  private DefaultMQProducer producer;
}
