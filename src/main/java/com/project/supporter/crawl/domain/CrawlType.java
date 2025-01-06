package com.project.supporter.crawl.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CrawlType {

    TAG("Tag"),

    WANTED_JOB("Wanted");

    private final String name;
}
