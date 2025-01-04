package com.project.supporter.crawl.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CrawlerFactory {

    private final List<Crawler> crawlers;

    public Crawler get(CrawlType crawlType) {
        return crawlers.stream()
                .filter(crawler -> crawler.isSupport(crawlType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 Crawler가 존재하지 않습니다. crawlType=" + crawlType));
    }
}
