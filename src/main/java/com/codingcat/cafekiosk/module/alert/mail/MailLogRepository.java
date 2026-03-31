package com.codingcat.cafekiosk.module.alert.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailLogRepository extends JpaRepository<MailLog,  Long> {

}
