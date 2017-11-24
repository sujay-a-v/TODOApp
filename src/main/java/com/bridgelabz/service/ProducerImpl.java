package com.bridgelabz.service;

import java.util.HashMap;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ProducerImpl implements Producer {

	@Autowired
	private RabbitTemplate template;
	
	@Autowired
	private Queue queue;
	
	@Override
	public void send(HashMap<String, String> map) {
		System.out.println("inside Producer impl");
		
		this.template.convertAndSend(queue.getName(),map);
		
		System.out.println(" mail sent " + map );
		
	}

}
