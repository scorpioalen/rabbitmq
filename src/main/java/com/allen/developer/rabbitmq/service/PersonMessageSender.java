package com.allen.developer.rabbitmq.service;

import com.allen.developer.rabbitmq.config.RabbitConfig;
import com.allen.developer.rabbitmq.json.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by a.roguljic on 2018-12-01
 */
@Service
public class PersonMessageSender {

	@Autowired
	ObjectMapper m_objectMapper;

	RabbitTemplate m_rabbitTemplate;

	@Autowired
	public PersonMessageSender(RabbitTemplate p_rabbitTemplate) {
		m_rabbitTemplate = p_rabbitTemplate;
	}

	@Scheduled(fixedDelay = 1000)
	public void sendObject() {
		try {
			String orderJSON = m_objectMapper.writeValueAsString(
					new Person("John", Math.random() + "", Math.random())
			);

			Message message = MessageBuilder
					.withBody(orderJSON.getBytes())
					.setContentType(MessageProperties.CONTENT_TYPE_JSON)
					.build();

			m_rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_PERSONS, message);
		} catch(JsonProcessingException p_e) {
			p_e.printStackTrace();
		}
	}

	@Scheduled(fixedDelay = 1000)
	public void sendString() {
		try {
			String orderJSON = m_objectMapper.writeValueAsString("Text: " + Math.random());

			Message message = MessageBuilder
					.withBody(orderJSON.getBytes())
					.setContentType(MessageProperties.CONTENT_TYPE_JSON)
					.build();

			m_rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_STRING, message);
		} catch(JsonProcessingException p_e) {
			p_e.printStackTrace();
		}
	}
}
