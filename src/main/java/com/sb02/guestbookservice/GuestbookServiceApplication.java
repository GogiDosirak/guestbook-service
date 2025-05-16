package com.sb02.guestbookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GuestbookServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(GuestbookServiceApplication.class, args);
  }

}
