package com.allen.developer.rabbitmq.service;

import com.allen.developer.rabbitmq.config.RabbitConfig;
import com.allen.developer.rabbitmq.json.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by a.roguljic on 2018-12-01
 */
@Component
public class PersonMessageListener {

	@RabbitListener(queues = {RabbitConfig.QUEUE_PERSONS})
	public void processOrder(Person p_person) {
		System.out.println("Person received: " + p_person.toString());
	}

	@RabbitListener(queues = {RabbitConfig.QUEUE_STRING})
	public void stringListener(String string) {
		System.out.println("String received: " + string);
	}
}
