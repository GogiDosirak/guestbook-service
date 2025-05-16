package com.sb02.guestbookservice;

import java.time.Instant;

public record GuestbookResponse(
    Long id,
    String name,
    String title,
    String content,
    String imageUrl,
    Instant createdAt
)
{
}
