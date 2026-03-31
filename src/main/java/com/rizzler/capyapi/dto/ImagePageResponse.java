package com.rizzler.capyapi.dto;

import java.util.List;

public record ImagePageResponse(
        List<ImageDto> items,
        int page,
        int size,
        long totalItems,
        int totalPages
) {}
