package com.sb02.guestbookservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/guestbooks")
public class GuestbookController {

  private final GuestbookService guestbookService;

  @PostMapping
  public ResponseEntity<GuestbookResponse> create(
      @ModelAttribute CreateGuestbookRequest createGuestbookRequest) {
    GuestbookResponse guestbookResponse = guestbookService.create(createGuestbookRequest);
    return ResponseEntity.ok().body(guestbookResponse);
  }

  @GetMapping("{id}")
  public ResponseEntity<GuestbookResponse> find(@PathVariable Long id) {
    GuestbookResponse guestbookResponse = guestbookService.find(id);
    return ResponseEntity.ok().body(guestbookResponse);
  }

  @GetMapping
  public ResponseEntity<PageResponse> findAll(
      @PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable) {
    PageResponse pageResponse = guestbookService.findAll(pageable);

    return ResponseEntity.ok().body(pageResponse);
  }
}
