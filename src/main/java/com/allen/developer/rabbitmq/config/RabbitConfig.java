package com.allen.developer.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by a.roguljic on 2018-12-01
 */
@Configuration
public class RabbitConfig {
	public static final String QUEUE_STRING = "string-queue";
	public static final String QUEUE_PERSONS = "persons-queue";

	@Bean
	Queue stringQueue() {
		return QueueBuilder.durable(QUEUE_STRING).build();
	}

	@Bean
	Queue personQueue() {
		return QueueBuilder.durable(QUEUE_PERSONS).build();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());

		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Configuration
	class DirectExchangeConfig {
		@Bean
		public Queue directQueue1() {
			return new AnonymousQueue();
		}

		@Bean
		public Queue directQueue2() {
			return new AnonymousQueue();
		}

		@Bean
		public DirectExchange direct() {
			return new DirectExchange("test.direct.exchange");
		}

		@Bean
		Binding stringQueueBinding1a(Queue directQueue1, DirectExchange exchange) {
			return BindingBuilder.bind(directQueue1)
					.to(exchange).with("orange");
		}

		@Bean
		Binding stringQueueBinding1b(Queue directQueue1, DirectExchange exchange) {
			return BindingBuilder.bind(directQueue1)
					.to(exchange).with("black");
		}

		@Bean
		Binding stringQueueBinding2a(Queue directQueue2, DirectExchange exchange) {
			return BindingBuilder.bind(directQueue2)
					.to(exchange).with("green");
		}

		@Bean
		Binding stringQueueBinding2b(Queue directQueue2, DirectExchange exchange) {
			return BindingBuilder.bind(directQueue2)
					.to(exchange).with("black");
		}
	}

	@Configuration
	class TopicExchangeConfig {
		@Bean
		public Queue topicQueue1() {
			return new AnonymousQueue();
		}

		@Bean
		public Queue topicQueue2() {
			return new AnonymousQueue();
		}

		@Bean
		public TopicExchange topic() {
			return new TopicExchange("test.topic.exchange");
		}

		@Bean
		public Binding binding1a(TopicExchange topic, Queue topicQueue1) {
			return BindingBuilder.bind(topicQueue1)
					.to(topic)
					.with("*.orange.*");
		}

		@Bean
		public Binding binding1b(TopicExchange topic, Queue topicQueue2) {
			return BindingBuilder.bind(topicQueue2)
					.to(topic)
					.with("*.*.rabbit");
		}

		@Bean
		public Binding binding2a(TopicExchange topic, Queue topicQueue2) {
			return BindingBuilder.bind(topicQueue2)
					.to(topic)
					.with("lazy.#");
		}
	}
}
