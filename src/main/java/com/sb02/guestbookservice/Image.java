package com.sb02.guestbookservice;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@NoArgsConstructor
@Getter
@Setter
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String fileName;
  private String contentType;
  private String s3Url;
  private String s3Key;
  private Long size;

  @OneToOne
  @JoinColumn(name = "guestbook_id")
  @JsonBackReference
  private Guestbook guestbook;

  public Image(String fileName, String contentType, String s3Url, String s3Key,
      Long size, Guestbook guestbook) {
    this.fileName = fileName;
    this.contentType = contentType;
    this.s3Url = s3Url;
    this.s3Key = s3Key;
    this.size = size;
    this.guestbook = guestbook;
  }

}
