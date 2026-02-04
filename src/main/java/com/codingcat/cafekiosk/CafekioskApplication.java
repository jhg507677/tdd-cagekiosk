package com.codingcat.cafekiosk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication // 스프링 부트 사용에 필요한 기본설정
public class CafekioskApplication {

  public static void main(String[] args) {
    // 첫 번째 인수는 스프링 부트 3 애플리케이션의 메인 클래스로 사용할 클래스
    // 두 번째 인수는 커맨드 라인의 인수들
    SpringApplication.run(CafekioskApplication.class, args);
  }

}


/*
@SpringBootConfiguration :  스프링 부트 관련 설정을 나타내는 애너테이션
@ComponentScan : 사용자가 등록한 빈을 읽고 등록하는 애너테이션, @Component라는 애너테이션을 가진 클래스들을 찾아 빈으로 등록하는 역할
@EnableAuthConfiguration : 자동 구성을 활성화하는 애너테이션, 스프링 부트의 메타 파일을 읽고 정의된 설정들을 자동으로 구성


@RestController : 라우터 역할을 하는 애너테이션
HTTP요청과 메서드를 연결하는 장치

*/