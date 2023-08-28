package dev.sobue.socket.smtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class.
 *
 * @author SOBUE Sho
 */
@SpringBootApplication
public class SocketSMTPApplication {

	/**
	 * Main method.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SocketSMTPApplication.class, args);
	}
}
