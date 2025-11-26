package dev.sobue.socket.smtp;

import jakarta.mail.internet.MailDateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.mail.autoconfigure.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Implementation for Java Socket.
 *
 * @author SOBUE Sho
 */
@Service
@EnableConfigurationProperties(MailProperties.class)
public class JavaSocketSendMailService implements SendMailService {

  /**
   * Line separator.
   */
  private static final String CRLF = "\r\n";

  /**
   * Hostname
   */
  private static final String MY_HOSTNAME;

  static {
    try {
      MY_HOSTNAME = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * SMTP Server Host
   */
  private final String serverHost;

  /**
   * SMTP Server Port
   */
  private final int serverPort;

  /**
   * Constructor.
   *
   * @param properties Configuration properties for email support.
   */
  public JavaSocketSendMailService(
      MailProperties properties
  ){
    this.serverHost = properties.getHost();
    this.serverPort = properties.getPort();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void send(SimpleMailMessage message) {
    sendInternal(
        message.getFrom(),
        message.getTo()[0],
        message.getSubject(),
        message.getText());
  }

  private void sendInternal(
      String sender,
      String receiver,
      String title,
      String message
  ) {
    try (var sock = new Socket(serverHost, serverPort);
        var reply = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        var send = new PrintStream(sock.getOutputStream())) {
      verifyResponse("220", reply.readLine());

      send.print("HELO " + MY_HOSTNAME + CRLF);
      send.flush();
      verifyResponse("250", reply.readLine());

      send.print("MAIL FROM: " + sender + CRLF);
      send.flush();
      verifyResponse("250", reply.readLine());

      send.print("RCPT TO: " + receiver + CRLF);
      send.flush();
      verifyResponse("250", reply.readLine());

      send.print("DATA" + CRLF);
      send.flush();
      verifyResponse("354", reply.readLine());
      
      send.print("From: " + sender + CRLF);
      send.print("To: " + receiver + CRLF);
      send.print("Subject: " + title + CRLF);
      send.print("Date: " + new MailDateFormat().format(new Date()) + CRLF);
      send.print(CRLF);
      send.print(message + CRLF);
      send.print(".");
      send.print(CRLF);
      send.flush();
      verifyResponse("250", reply.readLine());

      send.print("QUIT" + CRLF);
      send.flush();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private void verifyResponse(String status, String response) throws ProtocolException {
    if (!response.startsWith(status)) {
      throw new ProtocolException(response);
    }
  }
}
