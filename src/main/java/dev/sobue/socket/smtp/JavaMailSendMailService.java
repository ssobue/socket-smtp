package dev.sobue.socket.smtp;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Implementation for Java Mail.
 *
 * @author SOBUE Sho
 * @see jakarta.mail.internet.MimeMessage
 */
@Service
@RequiredArgsConstructor
public class JavaMailSendMailService implements SendMailService {

  /**
   * Spring Mail.
   */
  private final MailSender mailSender;

  /**
   * {@inheritDoc}
   */
  @Override
  public void send(SimpleMailMessage message) {
    mailSender.send(message);
  }
}
