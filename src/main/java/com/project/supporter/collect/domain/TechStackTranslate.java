package com.project.supporter.collect.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
public class TechStackTranslate {

    private final ObjectMapper objectMapper;

    @Value("${techstack.file.path:tech-stack.json}")
    private String techStackFilePath;

    private static final Pattern KOREAN_PATTERN = Pattern.compile("[가-힣]");

    private Map<String, String> techStackMap;

    @PostConstruct
    public void init() throws IOException {
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(techStackFilePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("tech-stack.json 파일을 찾을 수 없습니다: " + techStackFilePath);
            }
            techStackMap = Map.copyOf(objectMapper.readValue(inputStream, new TypeReference<>() {}));
        }
    }

    private boolean containsKorean(String input) {
        return Optional.ofNullable(input)
                .map(s -> KOREAN_PATTERN.matcher(s).find())
                .orElse(false);
    }

    private Optional<String> translateToEnglish(String koreanTechName) {
        return techStackMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(koreanTechName))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public void translateKoreanName(Tags tags) {
        tags.getTagCollection().forEach(tag -> {
            if (!containsKorean(tag.getName())) {
                return;
            }

            translateToEnglish(tag.getName())
                    .ifPresentOrElse(tag::updateTechName, () -> log.info("기술 스택 번역 실패: {}", tag.getName()));
        });
    }
}