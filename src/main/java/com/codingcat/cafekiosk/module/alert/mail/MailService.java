package com.codingcat.cafekiosk.module.alert.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {
  private final MailSendClient mailSendClient;
  private final MailLogRepository logRepository;

  public boolean sendMail(
    String senderEmail, String receiverEmail, String subject, String content
  ) {
    boolean result = mailSendClient.sendMail(senderEmail,  receiverEmail,  subject,  content);
    if(result){
      logRepository.save(MailLog.builder()
          .senderEmail(senderEmail)
          .receiverEmail(receiverEmail)
          .subject(subject)
          .content(content)
        .build());
      return true;
    }
    return false;
  }
}
