package com.allen.developer.rabbitmq.service;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by a.roguljic on 2018-12-01
 */
@Service
public class ExchangeSender {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange direct;

	@Autowired
	private TopicExchange topicExchange;

	private int index;
	private int count;

	private final String[] keys = {"orange", "black", "green"};

	private final String[] topicKeys = {
			"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
			"lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"
	};

	// @Scheduled(fixedDelay = 1000, initialDelay = 500)
	public void send() {
		sendMessage(false);
	}

	@Scheduled(fixedDelay = 1000, initialDelay = 500)
	public void topicSend() {
		sendMessage(true);
	}

	public void sendMessage(boolean isTopic) {
		StringBuilder builder = new StringBuilder("Message to ");

		if (++index == keys.length) {
			index = 0;
		}

		String key = isTopic ? topicKeys[index] : keys[index];
		builder.append(key).append(' ');
		builder.append(++this.count);

		String message = builder.toString();

		template.convertAndSend(isTopic ? topicExchange.getName() : direct.getName(), key, message);

		System.out.println(" [x] Sent '" + message + "'");
	}


}
