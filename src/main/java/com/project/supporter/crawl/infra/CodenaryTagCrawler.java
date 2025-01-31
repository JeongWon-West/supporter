package com.project.supporter.crawl.infra;

import com.project.supporter.crawl.domain.CrawlType;
import com.project.supporter.crawl.domain.TagCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class CodenaryTagCrawler implements TagCrawler {

    @Value("${collect.tag-collect-url}")
    private String tagCollectUrl;

    private final RestTemplate collectRestTemplate;

    @Override
    public boolean isSupport(CrawlType crawlType) {
        return CrawlType.TAG.equals(crawlType);
    }

    @Override
    public TechStackResponse collect() {
        TechStackResponse techStackResponse = collectTechStack(tagCollectUrl);

        while (techStackResponse.hasNext()) {
            String collectUrl = String.format("%s?after=%s", tagCollectUrl, techStackResponse.getEndCursor());
            TechStackResponse response = collectTechStack(collectUrl);
            techStackResponse.merge(response);
        }

        return techStackResponse;
    }

    private TechStackResponse collectTechStack(String collectUrl) {
        try {
            return collectRestTemplate.getForObject(collectUrl, TechStackResponse.class);
        } catch (HttpClientErrorException clientErrorException) {
            String errorMessage = String.format("태그 수집에 실패하였습니다. :: [%d / %s]", clientErrorException.getStatusCode().value(), clientErrorException.getMessage());
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
