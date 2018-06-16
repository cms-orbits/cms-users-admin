package com.joelgtsantos.cmsusers.entity;

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

  public void sendMessageRegister(Email email) {
	this.statusMessage = false;
	String routingKey = "user.created";
    rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, email);
    this.statusMessage = true;
  }
  
  public boolean statusMessage() {
	  return this.statusMessage;
  }
  
}