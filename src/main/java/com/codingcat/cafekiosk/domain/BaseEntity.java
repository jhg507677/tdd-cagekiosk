package com.codingcat.cafekiosk.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*공통 엔티티 필드를 자동으로 관리하기 위한 베이스 클래스*/
@Getter
@MappedSuperclass // 이 클래스를 상속한 엔티티는 이 클래스의 필드를 자기 컬럼처럼 가짐
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  @CreatedDate
  private LocalDateTime createdDateTime;

  @LastModifiedDate
  private LocalDateTime modifiedDateTime;
}
