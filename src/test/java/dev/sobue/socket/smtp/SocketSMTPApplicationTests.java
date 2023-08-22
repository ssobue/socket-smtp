package dev.sobue.socket.smtp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = "spring.mail.test-connection=false")
class SocketSMTPApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	@DisplayName("Test load ApplicationContext")
	void contextLoads() {
		assertNotNull(context);
	}
}
