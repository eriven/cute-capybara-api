package com.rizzler.capyapi.dto;
import java.util.List;
public record ImageDto(
        Long id,
        String title,
        String imageUrl,
        String sourceUrl,
        List<String> tags
) {}
