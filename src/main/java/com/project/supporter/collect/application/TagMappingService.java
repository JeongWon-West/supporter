package com.project.supporter.collect.application;

import com.project.supporter.collect.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

        jobTagRepository.deleteByJobIdx(job.getIdx());

        tagNames = translateTagNames(tagNames);
        Map<String, Tag> tagMap = createTagMap(tagNames);
        List<JobTag> jobTags = createJobTags(job, tagMap, tagNames);

        jobTagRepository.saveAll(jobTags);
    }

    private List<String> translateTagNames(List<String> tagNames) {
        return tagNames.stream()
                .map(techStackTranslate::translateKoreanName)
                .collect(Collectors.toList());
    }

    private Map<String, Tag> createTagMap(List<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNames(tagNames);
        return existingTags.stream()
                .collect(Collectors.toMap(Tag::getName, Function.identity()));
    }

    private List<JobTag> createJobTags(Job job, Map<String, Tag> tagMap, List<String> tagNames) {
        return tagNames.stream()
                .map(tagName -> {
                    Tag tag = tagMap.getOrDefault(tagName, Tag.builder()
                            .uniqueTagId(tagName)
                            .name(tagName)
                            .build());
                    return createJobTag(job, tag);
                })
                .collect(Collectors.toList());
    }

    private JobTag createJobTag(Job job, Tag tag) {
        return JobTag.builder()
                .job(job)
                .tag(tag)
                .build();
    }
}
