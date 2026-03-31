package com.rizzler.capyapi.controller;

import com.rizzler.capyapi.dto.ImageDto;
import com.rizzler.capyapi.dto.ImagePageResponse;
import com.rizzler.capyapi.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@Validated
@Tag(name = "Images", description = "Browse and discover capybara images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    @Operation(
        summary = "List images",
        description = "Returns a paginated list of capybara images. Optionally filter by tag."
    )
    @ApiResponse(responseCode = "200", description = "Paginated image list")
    @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    public ResponseEntity<ImagePageResponse> getImages(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size (1–100)", example = "12")
            @RequestParam(defaultValue = "12") @Min(1) @Max(100) int size,
            @Parameter(description = "Filter by tag (case-insensitive)", example = "chill")
            @RequestParam(required = false) String tag
    ) {
        return ResponseEntity.ok(imageService.getImages(page, size, tag));
    }

    @GetMapping("/random")
    @Operation(
        summary = "Get a random image",
        description = "Returns a single randomly selected capybara image."
    )
    @ApiResponse(responseCode = "200", description = "A random image")
    @ApiResponse(responseCode = "404", description = "No images found")
    public ResponseEntity<ImageDto> getRandomImage() {
        return ResponseEntity.ok(imageService.getRandomImage());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get image by ID",
        description = "Returns a single capybara image by its ID."
    )
    @ApiResponse(responseCode = "200", description = "Image found",
        content = @Content(schema = @Schema(implementation = ImageDto.class)))
    @ApiResponse(responseCode = "404", description = "Image not found")
    @ApiResponse(responseCode = "400", description = "Invalid ID format")
    public ResponseEntity<ImageDto> getImageById(
            @Parameter(description = "Image ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }
}
