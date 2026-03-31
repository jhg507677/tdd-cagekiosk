package com.codingcat.cafekiosk.module.alert.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

class MailServiceTest {
  @DisplayName("================ 메일 전송 테스트 ================ ")
  @Test
  void sendMail(){
    // given
    // = @Mock MailSendClient mailSendClient 같음
    // 대신 @ExtendWith(MockitoExtension.class) 어노테이션 추가
    MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
    MailLogRepository mailLogRepository = Mockito.mock(MailLogRepository.class);

    // = @InjectMcoks
    MailService mailService = new MailService(mailSendClient, mailLogRepository);

    // stubbing
    // 위 아래는 같은 문법
//    Mockito.when(mailSendClient.sendEmail(
//      anyString(),any(String.class),any(String.class),any(String.class)
//    )).thenReturn(true);
    BDDMockito.given(mailSendClient.sendMail(
      anyString(),any(String.class),any(String.class),any(String.class)
    )).willReturn(true);

// 이렇게 하지 않아도 mock이 자동으로 null을 뱉기 때문에 exception이 발생하지 는 않음
//    Mockito.when(mailLogRepository.save(any(MailLog.class)));

    // when
    boolean result = mailService.sendMail("","","","");

    // mailLogRepository.save 메서드가 1번 호출되었니?
    Mockito.verify(mailLogRepository, Mockito.times(1))
      .save(any(MailLog.class));

    // then
    assertThat(result).isTrue();
  }
}