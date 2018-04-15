package com.cms.users.entity;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class EventPublisher {

 
  private final RabbitTemplate rabbitTemplate;

  private final TopicExchange topicExchange;
  
  private boolean statusMessage;


  @Autowired
  public EventPublisher(RabbitTemplate rabbitTemplate, TopicExchange topicExchange) {
    this.rabbitTemplate = rabbitTemplate;
    this.topicExchange = topicExchange;
  }

  public void sendMessage() {
	this.statusMessage = false;
	String routingKey = "customer.created";
	String message = "customer created";
    rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, message);
    this.statusMessage = true;
  }
  
  public boolean statusMessage() {
	  return this.statusMessage;
  }
  
}