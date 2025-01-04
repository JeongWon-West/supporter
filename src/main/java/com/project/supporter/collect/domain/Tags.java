package com.project.supporter.collect.domain;

import com.project.supporter.crawl.domain.CollectResponse;
import com.project.supporter.crawl.infra.TechStackResponse;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Tags {

    private List<Tag> tagCollection;

    public Tags(List<Tag> tagCollection) {
        this.tagCollection = tagCollection;
    }

    public static Tags of(CollectResponse collectResponse) {
        if (!(collectResponse instanceof TechStackResponse techStackResponse)) {
            throw new IllegalArgumentException("convert 가능한 collectResponse가 아닙니다.");
        }

        List<Tag> collectTechs = techStackResponse.getTechStacks().stream()
                .map(stack -> Tag.builder()
                        .uniqueTagId(stack.getTechstack().getId())
                        .category(stack.getTechstack().getCategory())
                        .description(stack.getTechstack().getDescription())
                        .name(stack.getTechstack().getName())
                        .build()
                )
                .collect(Collectors.toList());

        return new Tags(collectTechs);
    }

    public void exceptExistTags(TagRepository tagRepository) {
        if (tagCollection.isEmpty()) {
            return;
        }
        List<String> uniqueTagIds = tagCollection.stream()
                .map(Tag::getUniqueTagId)
                .toList();
        Set<Tag> existTags = tagRepository.findAllByUniqueTagIdIn(uniqueTagIds);

        this.tagCollection = tagCollection.stream()
                .filter(tag -> !existTags.contains(tag))
                .toList();
    }

    public boolean isEmpty() {
        return tagCollection.isEmpty();
    }
}
