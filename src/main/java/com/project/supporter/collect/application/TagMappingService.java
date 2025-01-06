package com.project.supporter.collect.application;

import com.project.supporter.collect.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TagMappingService {

    private final TagRepository tagRepository;

    private final JobTagRepository jobTagRepository;

    private final TechStackTranslate techStackTranslate;

    public void mapAndSaveJobTags(Job job, List<String> tagNames) {
        if (CollectionUtils.isEmpty(tagNames)) {
            return;
        }

        // 기존 JobTag 삭제
        jobTagRepository.deleteByJobIdx(job.getIdx());

        tagNames = tagNames.stream()
                .map(techStackTranslate::translateKoreanName)
                .collect(Collectors.toList());

        // 존재하는 태그들 조회
        List<Tag> existingTags = tagRepository.findByNames(tagNames);
        Map<String, Tag> tagMap = existingTags.stream()
                .collect(Collectors.toMap(Tag::getName, tag -> tag));

        // JobTag 엔티티 생성 및 저장
        List<JobTag> jobTags = tagNames.stream()
                .map(tagName -> {
                    Tag tag = tagMap.get(tagName);
                    if (tag == null) {
                        log.warn("Tag not found: {}", tagName);
                        tag = Tag.builder()
                                .uniqueTagId(tagName)
                                .name(tagName)
                                .build();
                    }
                    return createJobTag(job, tag);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        jobTagRepository.saveAll(jobTags);
    }

    private JobTag createJobTag(Job job, Tag tag) {
        return JobTag.builder()
                .job(job)
                .tag(tag)
                .build();
    }
}
