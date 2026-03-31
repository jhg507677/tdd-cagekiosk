package com.codingcat.cafekiosk.module.alert.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// 테스트를 위해서 해당 서비스 mocking 필요
@Slf4j
@Component
public class MailSendClient {

  public boolean sendMail(String senderEmail, String receiverEmail, String subject, String content) {
    log.debug("===================== 메일 전송 =====================");
    return true;
  }
}
