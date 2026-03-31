package com.rizzler.capyapi.controller;

import com.rizzler.capyapi.dto.ImageDto;
import com.rizzler.capyapi.dto.ImagePageResponse;
import com.rizzler.capyapi.exception.GlobalExceptionHandler;
import com.rizzler.capyapi.exception.ResourceNotFoundException;
import com.rizzler.capyapi.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImageController.class)
@Import(GlobalExceptionHandler.class)
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    void getImages_defaultParams() throws Exception {
        var response = new ImagePageResponse(
                List.of(new ImageDto(1L, "Test", "https://img.com/1.jpg", "https://src.com", List.of("test-tag"))),
                0, 12, 1, 1
        );
        when(imageService.getImages(0, 12, null)).thenReturn(response);

        mockMvc.perform(get("/api/images"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Test"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(12))
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void getImages_customParams() throws Exception {
        var response = new ImagePageResponse(List.of(), 2, 5, 0, 0);
        when(imageService.getImages(2, 5, null)).thenReturn(response);

        mockMvc.perform(get("/api/images?page=2&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(2))
                .andExpect(jsonPath("$.size").value(5));
    }

    @Test
    void getImages_invalidSize_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/images?size=0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getImages_negativePage_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/images?page=-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getImageById_found() throws Exception {
        var dto = new ImageDto(1L, "Test", "https://img.com/1.jpg", "https://src.com", List.of());
        when(imageService.getImageById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/images/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test"));
    }

    @Test
    void getImageById_notFound() throws Exception {
        when(imageService.getImageById(99L)).thenThrow(new ResourceNotFoundException("Image not found with id: 99"));

        mockMvc.perform(get("/api/images/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Image not found with id: 99"));
    }

    @Test
    void getImageById_invalidId_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/images/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRandomImage_success() throws Exception {
        var dto = new ImageDto(7L, "Random", "https://img.com/7.jpg", "https://src.com", List.of("random"));
        when(imageService.getRandomImage()).thenReturn(dto);

        mockMvc.perform(get("/api/images/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.title").value("Random"))
                .andExpect(jsonPath("$.tags[0]").value("random"));
    }

    @Test
    void getImages_withTagFilter() throws Exception {
        var response = new ImagePageResponse(
                List.of(new ImageDto(1L, "Test", "https://img.com/1.jpg", "https://src.com", List.of("cute"))),
                0, 12, 1, 1
        );
        when(imageService.getImages(0, 12, "cute")).thenReturn(response);

        mockMvc.perform(get("/api/images?tag=cute"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].tags[0]").value("cute"));
    }
}
