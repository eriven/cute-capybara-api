package com.rizzler.capyapi.config;

import com.rizzler.capyapi.model.Image;
import com.rizzler.capyapi.repository.ImageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.data.domain.PageRequest;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ImageRepository imageRepository;

    public DataSeeder(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void run(String... args) {
        if (imageRepository.count() > 0) {
            // Check if existing data has tags — if not, it's stale Phase 1 data
            boolean hasTags = imageRepository.findAll(PageRequest.of(0, 1))
                    .getContent().stream()
                    .anyMatch(img -> img.getTags() != null && !img.getTags().isEmpty());
            if (hasTags) {
                return;
            }
            // Stale data from Phase 1, delete and reseed with tags
            imageRepository.deleteAll();
        }

        List<Image> images = List.of(
                new Image("Capybara chilling in hot spring",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Capybara_%28Hydrochoerus_hydrochaeris%29.JPG/1280px-Capybara_%28Hydrochoerus_hydrochaeris%29.JPG",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("chill", "water", "hot-spring")),
                new Image("Capybara with friends",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Capybara_portrait.jpg/1280px-Capybara_portrait.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("friends", "group", "social")),
                new Image("Capybara family by the river",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Capybara_Babies.jpg/1280px-Capybara_Babies.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("family", "river", "baby")),
                new Image("Capybara swimming gracefully",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Capybara_in_a_zoo_%282%29.jpg/1280px-Capybara_in_a_zoo_%282%29.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("swimming", "water", "graceful")),
                new Image("Capybara sunbathing",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Capybara_Hattiesburg_Zoo_%2870909b-42%29_2560x1600.jpg/1280px-Capybara_Hattiesburg_Zoo_%2870909b-42%29_2560x1600.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("sunbathing", "chill", "sunny")),
                new Image("Capybara vibing with ducks",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Capybara_%28Hydrochoerus_hydrochaeris%29.JPG/800px-Capybara_%28Hydrochoerus_hydrochaeris%29.JPG",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("vibing", "ducks", "friends")),
                new Image("Baby capybara close-up",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Capybara_portrait.jpg/800px-Capybara_portrait.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("baby", "close-up", "portrait")),
                new Image("Capybara in the wild",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Capybara_Babies.jpg/800px-Capybara_Babies.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("wild", "nature")),
                new Image("Capybara at sunset",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Capybara_in_a_zoo_%282%29.jpg/800px-Capybara_in_a_zoo_%282%29.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("sunset", "aesthetic")),
                new Image("Capybara being unbothered",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Capybara_Hattiesburg_Zoo_%2870909b-42%29_2560x1600.jpg/800px-Capybara_Hattiesburg_Zoo_%2870909b-42%29_2560x1600.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("unbothered", "chill", "mood")),
                new Image("Capybara squad goals",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Capybara_%28Hydrochoerus_hydrochaeris%29.JPG/640px-Capybara_%28Hydrochoerus_hydrochaeris%29.JPG",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("squad", "group", "goals")),
                new Image("Capybara nose boop",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Capybara_portrait.jpg/640px-Capybara_portrait.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("boop", "cute", "close-up")),
                new Image("Capybara zen mode",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Capybara_Babies.jpg/640px-Capybara_Babies.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("zen", "meditation", "calm")),
                new Image("Capybara living its best life",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Capybara_in_a_zoo_%282%29.jpg/640px-Capybara_in_a_zoo_%282%29.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("best-life", "happy")),
                new Image("Capybara ultimate chill",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Capybara_Hattiesburg_Zoo_%2870909b-42%29_2560x1600.jpg/640px-Capybara_Hattiesburg_Zoo_%2870909b-42%29_2560x1600.jpg",
                        "https://en.wikipedia.org/wiki/Capybara", List.of("ultimate", "chill", "relax"))
        );

        imageRepository.saveAll(images);
    }
}
