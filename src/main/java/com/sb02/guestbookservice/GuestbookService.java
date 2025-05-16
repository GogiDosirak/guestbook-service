package com.sb02.guestbookservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestbookService {

  private final GuestbookRepository guestbookRepository;
  private final ImageService imageService;

  @Transactional
  public GuestbookResponse create(CreateGuestbookRequest createGuestbookRequest) {
    Guestbook guestbook = new Guestbook(createGuestbookRequest.name(),
        createGuestbookRequest.title(), createGuestbookRequest.content());

    guestbookRepository.save(guestbook);

    ImageResponse imageResponse = null;
    if (createGuestbookRequest.image() != null) {
      imageResponse = imageService.uploadFile(createGuestbookRequest.image(),
          guestbook);
    }

    String imageUrl = (imageResponse != null) ? imageResponse.s3Url() : null;

    return new GuestbookResponse(guestbook.getId(), guestbook.getName(), guestbook.getTitle(),
        guestbook.getContent(), imageUrl, guestbook.getCreatedAt());
  }


  @Transactional(readOnly = true)
  public GuestbookResponse find(Long id) {
    Guestbook guestbook = guestbookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("guestbook not found: " + id));

    Image image = guestbook.getImage();

    return new GuestbookResponse(guestbook.getId(),
        guestbook.getName(),
        guestbook.getTitle(),
        guestbook.getContent(),
        image != null ? image.getS3Url() : null,
        guestbook.getCreatedAt());
  }

  @Transactional(readOnly = true)
  public PageResponse findAll(Pageable pageable) {
    Page<Guestbook> guestbooks = guestbookRepository.findAll(pageable);

    Page<GuestbookResponse> guestbookResponses = guestbooks.map(
        guestbook -> new GuestbookResponse(guestbook.getId(), guestbook.getName(),
            guestbook.getTitle(), guestbook.getContent(), guestbook.getImage() != null ? guestbook.getImage().getS3Url() : null,
            guestbook.getCreatedAt()));

    return new PageResponse(guestbookResponses.getContent(), guestbooks.getTotalPages(),
        guestbooks.getTotalElements(), guestbooks.getSize(), guestbooks.getNumber());
  }

}
