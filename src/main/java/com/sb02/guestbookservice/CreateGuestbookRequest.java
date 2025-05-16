package com.sb02.guestbookservice;

import org.springframework.web.multipart.MultipartFile;

public record CreateGuestbookRequest(
    String name,
    String title,
    String content,
    MultipartFile image
) {
}
