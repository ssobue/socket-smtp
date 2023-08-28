package dev.sobue.socket.smtp;

import org.springframework.mail.SimpleMailMessage;

/**
 * Send Mail Interface
 *
 * @author SOBUE Sho
 */
public interface SendMailService {

  /**
   * Send a message.
   *
   * @param message message object.
   */
  void send(SimpleMailMessage message);
}
