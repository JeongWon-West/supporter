package com.project.supporter.crawl.application;

import com.project.supporter.collect.application.JobService;
import com.project.supporter.collect.application.RecruitService;
import com.project.supporter.collect.application.TagService;
import com.project.supporter.collect.domain.RecruitSite;
import com.project.supporter.collect.domain.Status;
import com.project.supporter.crawl.domain.*;
import com.project.supporter.crawl.infra.JobDataCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlService {

    private final CrawlerFactory crawlerFactory;

    private final TagService tagService;

    private final RecruitService recruitService;

    private final JobService jobService;

    public void collectTag() {
        TagCrawler crawler = (TagCrawler) crawlerFactory.get(CrawlType.TAG);
        CollectResponse collect = crawler.collect();
        tagService.saveTagsIfNotExist(collect);
    }

    public void collectJob() {
        List<RecruitSite> recruitSites = recruitService.getRecruitSitesByStatus(Status.ACTIVE);

        // 병렬로 처리 시 서버 스펙상 이슈 있을 수 있어 순차적으로 처리
        recruitSites.forEach(recruitSite -> {
            CrawlType type = CrawlType.valueOf(recruitSite.getName());
            JobCrawler crawler = (JobCrawler) crawlerFactory.get(type);

            JobDataCollection collect;
            String nextCursor = null;
            do {
                collect = crawler.collectList(recruitSite.getName(), nextCursor);
                List<String> existingUniqueJobIds = jobService.findExistingUniqueJobIds(recruitSite.getName(), collect.getUniqueJobIds());
                collect.extractExistJobData(existingUniqueJobIds);
                if (collect.isEmpty()) {
                    break;
                }
                crawler.collectDetail(collect);
                jobService.saveJobData(collect.getJobDataList());
                collect.clearJobData();
                nextCursor = collect.getNextCursor();
            } while (collect.hasNext());
        });
    }
}
