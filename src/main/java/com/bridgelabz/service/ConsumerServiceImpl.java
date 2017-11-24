package com.bridgelabz.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;


public class ConsumerServiceImpl implements MessageListener {

	@Autowired
	Mailer mailer;
	
	@Override
	public void onMessage(Message message) {
		
		byte body[] = message.getBody();
		Map map=(Map) SerializationUtils.deserialize(body);
		mailer.send(map.get("to")+"", map.get("message")+"");
	}

}
