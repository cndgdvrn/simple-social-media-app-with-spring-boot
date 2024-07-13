package com.smapp.sm_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smapp.sm_app.pojo.MailMessageContent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@SpringBootApplication
@RequiredArgsConstructor
public class SmAppApplication {

	@Autowired
	private final RabbitTemplate rabbitTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SmAppApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner runner(){
//		return args -> {
//			for (int i = 0; i < 10; i++) {
//				String username = "username" + i;
//				String email = "email" + i + "@mail.com";
//				String activationToken = "activationToken" + i;
//				MailMessageContent mailMessageContent = new MailMessageContent(username, email, activationToken);
//
//				rabbitTemplate.convertAndSend("topic_exchange",
//						"activation", mailMessageContent);
//			}
//		};
//	}

}
