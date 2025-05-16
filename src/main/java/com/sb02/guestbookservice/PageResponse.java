package com.sb02.guestbookservice;

import java.util.List;

public record PageResponse(
    List<GuestbookResponse> content,
    long totalPages,
    long totalElements,
    long size,
    long number
    )
{
}
