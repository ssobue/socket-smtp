package dev.sobue.socket.smtp;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Spring Shell Component.
 *
 * @author SOBUE Sho
 */
@ShellComponent("Send Mail")
@RequiredArgsConstructor
public class SendMailCommand {

  /**
   * @see JavaSocketSendMailService
   */
  private final SendMailService javaSocketSendMailService;

  /**
   * @see JavaMailSendMailService
   */
  private final SendMailService javaMailSendMailService;

  @ShellMethod("Send mail using Java socket")
  public void javaSocket(String from, String to) {
    var message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject("TEST MAIL");
    message.setText("Java TEST Message using Java Socket");
    javaSocketSendMailService.send(message);
  }

  @ShellMethod("Send mail using Java Mail")
  public void javaMail(String from, String to) {
    var message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSentDate(new Date());
    message.setSubject("TEST MAIL");
    message.setText("Java TEST Message using Java Mail");
    javaMailSendMailService.send(message);
  }
}
