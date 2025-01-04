package com.project.supporter.crawl.domain;

public interface Crawler {

    boolean isSupport(CrawlType crawlType);

    CollectResponse collect();
}
