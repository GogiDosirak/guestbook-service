package com.sb02.guestbookservice;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name ="guestbooks")
@NoArgsConstructor
@Getter
@Setter
public class Guestbook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String title;
  private String content;
  @CreationTimestamp
  private Instant createdAt;

  @OneToOne(mappedBy = "guestbook", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private Image image;

  public Guestbook(String name, String title, String content) {
    this.name = name;
    this.title = title;
    this.content = content;
  }
}
