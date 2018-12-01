package com.allen.developer.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by a.roguljic on 2018-12-01
 */
@Component
public class ExchangeReceiver {

	@RabbitListener(queues = "#{directQueue1.name}")
	public void directReceiver1(String in) {
		receiveMessage(in, 1, "DIRECT");
	}

	@RabbitListener(queues = "#{directQueue2.name}")
	public void directReceiver2(String in) {
		receiveMessage(in, 2, "DIRECT");
	}

	@RabbitListener(queues = "#{topicQueue1.name}")
	public void topicReceiver1(String in) {
		receiveMessage(in, 1, "TOPIC");
	}

	@RabbitListener(queues = "#{topicQueue2.name}")
	public void topicReceiver2(String in){
		receiveMessage(in, 2, "TOPIC");
	}

	public void receiveMessage(String in, int receiver, String type) {
		System.out.println("Instance " + receiver + " [" + type + "] Received '" + in + "'");
	}

}

