package com.rizzler.capyapi.service;

import com.rizzler.capyapi.dto.ImageDto;
import com.rizzler.capyapi.dto.ImagePageResponse;
import com.rizzler.capyapi.exception.ResourceNotFoundException;
import com.rizzler.capyapi.model.Image;
import com.rizzler.capyapi.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    private Image sampleImage;

    @BeforeEach
    void setUp() {
        sampleImage = new Image("Test Capybara", "https://example.com/img.jpg", "https://example.com", List.of("test"));
        sampleImage.setId(1L);
    }

    @Test
    void getImages_returnsPagedResponse() {
        Page<Image> page = new PageImpl<>(
                List.of(sampleImage),
                PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "createdAt")),
                1
        );
        when(imageRepository.findAll(any(Pageable.class))).thenReturn(page);

        ImagePageResponse response = imageService.getImages(0, 12, null);

        assertEquals(1, response.items().size());
        assertEquals(0, response.page());
        assertEquals(12, response.size());
        assertEquals(1, response.totalItems());
        assertEquals(1, response.totalPages());
    }

    @Test
    void getImages_emptyPage() {
        Page<Image> page = new PageImpl<>(
                List.of(),
                PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "createdAt")),
                0
        );
        when(imageRepository.findAll(any(Pageable.class))).thenReturn(page);

        ImagePageResponse response = imageService.getImages(0, 12, null);

        assertTrue(response.items().isEmpty());
        assertEquals(0, response.totalItems());
    }

    @Test
    void getImageById_found() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(sampleImage));

        ImageDto dto = imageService.getImageById(1L);

        assertEquals(1L, dto.id());
        assertEquals("Test Capybara", dto.title());
        assertEquals("https://example.com/img.jpg", dto.imageUrl());
        assertEquals(1, dto.tags().size());
        assertEquals("test", dto.tags().get(0));
    }

    @Test
    void getImageById_notFound_throwsException() {
        when(imageRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> imageService.getImageById(99L));
    }

    @Test
    void getImages_mapsDtoCorrectly() {
        Page<Image> page = new PageImpl<>(
                List.of(sampleImage),
                PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "createdAt")),
                1
        );
        when(imageRepository.findAll(any(Pageable.class))).thenReturn(page);

        ImagePageResponse response = imageService.getImages(0, 12, null);
        ImageDto dto = response.items().get(0);

        assertEquals("Test Capybara", dto.title());
        assertEquals("https://example.com/img.jpg", dto.imageUrl());
        assertEquals("https://example.com", dto.sourceUrl());
        assertEquals(1, dto.tags().size());
    }

    @Test
    void getImages_withTag_callsFindByTag() {
        Page<Image> page = new PageImpl<>(
                List.of(sampleImage),
                PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "createdAt")),
                1
        );
        when(imageRepository.findByTag(any(String.class), any(Pageable.class))).thenReturn(page);

        ImagePageResponse response = imageService.getImages(0, 12, "test");
        assertEquals(1, response.items().size());
        assertEquals("test", response.items().get(0).tags().get(0));
    }

    @Test
    void getRandomImage_returnsItem() {
        when(imageRepository.count()).thenReturn(5L);
        Page<Image> page = new PageImpl<>(List.of(sampleImage));
        when(imageRepository.findAll(any(PageRequest.class))).thenReturn(page);

        ImageDto dto = imageService.getRandomImage();
        assertEquals(1L, dto.id());
    }
}
