package com.project.supporter.collect.application;

import com.project.supporter.collect.domain.TagRepository;
import com.project.supporter.collect.domain.Tags;
import com.project.supporter.collect.domain.TechStackTranslate;
import com.project.supporter.crawl.domain.CollectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final TechStackTranslate techStackTranslate;

    @Transactional
    public void saveTagsIfNotExist(CollectResponse collectResponse) {
        Tags tags = Tags.of(collectResponse);
        tags.exceptExistTags(tagRepository);
        if (tags.isEmpty()) {
            log.info("저장할 태그가 없습니다.");
            return;
        }
        techStackTranslate.translateKoreanName(tags);
        tagRepository.saveAll(tags.getTagCollection());
    }
}
