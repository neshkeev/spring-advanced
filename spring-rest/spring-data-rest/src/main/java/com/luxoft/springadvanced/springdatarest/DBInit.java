package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.App;
import com.luxoft.springadvanced.springdatarest.model.Review;
import com.luxoft.springadvanced.springdatarest.model.ReviewRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class DBInit {

    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    public void postConstruct() throws IOException {
        for (var app : AppNames.values()) {
            reviewRepository.saveAll(app.getData());
        }
    }

    private enum AppNames {
        DROPBOX("Dropbox", "/Dropbox.csv"),
        NETFLIX("Netflix", "/Netflix.csv"),
        SPOTIFY("Spotify", "/Spotify.csv"),
        TIKTOK("TikTok", "/TikTok.csv");

        public static final CSVFormat PARSER = CSVFormat.DEFAULT.builder()
                .setSkipHeaderRecord(true)
                .setHeader("reviewId", "content", "score")
                .build();

        private final String name;
        private final String location;

        AppNames(String name, String location) {
            this.name = name;
            this.location = location;
        }

        public @Nonnull List<Review> getData() throws IOException {
            final App app = new App();
            app.setId(UUID.nameUUIDFromBytes(name.getBytes()));
            app.setName(name);

            try (var resourceAsStream = new InputStreamReader(
                    Objects.requireNonNull(DBInit.class.getResourceAsStream(location)))) {
                return PARSER
                        .parse(resourceAsStream)
                        .stream()
                        .map(e -> getReview(app, e))
                        .collect(Collectors.toList());
            }
        }

        private static @Nonnull Review getReview(App app, CSVRecord e) {
            return new Review(UUID.fromString(e.get("reviewId")),
                    app,
                    e.get("content"),
                    Byte.parseByte(e.get("score")), 0);
        }
    }
}
