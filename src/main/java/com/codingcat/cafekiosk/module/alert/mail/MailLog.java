package com.codingcat.cafekiosk.module.alert.mail;

import com.codingcat.cafekiosk.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="mail_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MailLog extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String senderEmail;
  private String receiverEmail;
  private String subject;
  private String content;

  @Builder
  public MailLog(String senderEmail, String receiverEmail, String subject, String content) {
    this.senderEmail = senderEmail;
    this.receiverEmail = receiverEmail;
    this.subject = subject;
    this.content = content;
  }
}
