package com.rizzler.capyapi.service;

import com.rizzler.capyapi.dto.ImageDto;
import com.rizzler.capyapi.dto.ImagePageResponse;
import com.rizzler.capyapi.exception.ResourceNotFoundException;
import com.rizzler.capyapi.model.Image;
import com.rizzler.capyapi.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional(readOnly = true)
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;
    private final Random random = new Random();

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Cacheable(value = "images", key = "#page + '-' + #size + '-' + (#tag != null ? #tag.toLowerCase() : 'all')")
    public ImagePageResponse getImages(int page, int size, String tag) {
        if (tag != null && !tag.isBlank()) {
            log.info("Fetching images with tag='{}' page={} size={}", tag.trim(), page, size);
        } else {
            log.info("Fetching all images page={} size={}", page, size);
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Image> imagePage;

        if (tag != null && !tag.isBlank()) {
            imagePage = imageRepository.findByTag(tag.trim(), pageRequest);
        } else {
            imagePage = imageRepository.findAll(pageRequest);
        }

        var items = imagePage.getContent().stream()
                .map(this::toDto)
                .toList();

        log.info("Returning {} images (total={})", items.size(), imagePage.getTotalElements());

        return new ImagePageResponse(
                items,
                imagePage.getNumber(),
                imagePage.getSize(),
                imagePage.getTotalElements(),
                imagePage.getTotalPages()
        );
    }

    @Cacheable(value = "image", key = "#id")
    public ImageDto getImageById(Long id) {
        log.info("Fetching image id={}", id);
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
        return toDto(image);
    }

    public ImageDto getRandomImage() {
        long count = imageRepository.count();
        if (count == 0) {
            throw new ResourceNotFoundException("No images found");
        }
        int randomPage = random.nextInt((int) count);
        Page<Image> imagePage = imageRepository.findAll(PageRequest.of(randomPage, 1));
        if (!imagePage.hasContent()) {
            throw new ResourceNotFoundException("No images found");
        }
        ImageDto dto = toDto(imagePage.getContent().get(0));
        log.info("Returning random image id={}", dto.id());
        return dto;
    }

    private ImageDto toDto(Image image) {
        return new ImageDto(
                image.getId(),
                image.getTitle(),
                image.getImageUrl(),
                image.getSourceUrl(),
                image.getTags()
        );
    }
}
