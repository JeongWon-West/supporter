package com.project.supporter.crawl.application;

import com.project.supporter.collect.application.TagService;
import com.project.supporter.crawl.domain.CollectResponse;
import com.project.supporter.crawl.domain.CrawlType;
import com.project.supporter.crawl.domain.Crawler;
import com.project.supporter.crawl.domain.CrawlerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlService {

    private final CrawlerFactory crawlerFactory;

    private final TagService tagService;

    public void collectTag() {
        Crawler crawler = crawlerFactory.get(CrawlType.TAG);
        CollectResponse collect = crawler.collect();
        tagService.saveTagsIfNotExist(collect);
    }

}
