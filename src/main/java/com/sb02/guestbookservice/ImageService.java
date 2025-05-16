package com.sb02.guestbookservice;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

  private final S3Client s3Client;
  private final ImageRepository imageRepository;

  @Value("${aws.s3.bucket}")
  private String bucketName;

  @Value("${aws.s3.base-url}")
  private String baseUrl;

  public ImageResponse uploadFile(MultipartFile file, Guestbook guestbook) {
    String fileName = file.getOriginalFilename();
    String contentType = file.getContentType();
    long size = file.getSize();

    String s3Key = UUID.randomUUID().toString() + "-" + fileName;

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(s3Key)
        .contentType(contentType)
        .build();

    try {
      s3Client.putObject(putObjectRequest,
          RequestBody.fromInputStream(file.getInputStream(), size));
    } catch (IOException e) {
      log.error("Error uploading file to S3", e);
      throw new RuntimeException("Error uploading file to S3", e);
    }

    String s3Url = baseUrl + "/" + s3Key;

    Image image = new Image(fileName, contentType, s3Url, s3Key, size, guestbook);
    image = imageRepository.save(image);

    log.info("File uploaded successfully: {}", s3Key);

    return new ImageResponse(
        image.getS3Url());
  }

  public ImageResponse getImageById(Long id) {
    Image image = imageRepository.findByGuestbookId(id)
        .orElseThrow(() -> new RuntimeException("File not found with id: " + id));

    return new ImageResponse(
        image.getS3Url()
    );
  }
}
