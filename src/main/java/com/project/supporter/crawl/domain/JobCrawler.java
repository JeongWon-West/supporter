package com.project.supporter.crawl.domain;

import com.project.supporter.crawl.infra.JobDataCollection;


public interface JobCrawler extends Crawler {

    JobDataCollection collectList(String recruitSiteName, String nextCursor);

    void collectDetail(JobDataCollection jobDataCollection);

}
