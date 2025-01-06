package com.project.supporter.crawl.infra;

import com.project.supporter.crawl.domain.CrawlType;
import com.project.supporter.crawl.domain.JobCrawler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WantedJobCrawler implements JobCrawler {

    @Value("${collect.wanted.list-url-prefix}")
    private String listUrlPrefix;

    @Value("${collect.wanted.list-url-cursor}")
    private String listUrlCursor;

    @Value("${collect.wanted.detail-url}")
    private String detailUrl;

    private final RestTemplate collectRestTemplate;

    @Override
    public boolean isSupport(CrawlType crawlType) {
        return CrawlType.WANTED_JOB.equals(crawlType);
    }

    @Override
    public JobDataCollection collectList(String recruitSiteName, String nextCursor) {
        WantedJobList wantedJobList = collectWantedJobList(nextCursor);
        return wantedJobList.convert(recruitSiteName);
    }

    private WantedJobList collectWantedJobList(String nextCursor) {
        try {
            String jobListUrl = Objects.isNull(nextCursor) ? listUrlPrefix + listUrlCursor : listUrlPrefix + nextCursor;
            return collectRestTemplate.getForObject(jobListUrl, WantedJobList.class);
        } catch (HttpClientErrorException clientErrorException) {
            String errorMessage = String.format("Wanted 채용공고 수집에 실패하였습니다. :: [%d / %s]", clientErrorException.getStatusCode().value(), clientErrorException.getMessage());
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @Override
    public void collectDetail(JobDataCollection jobDataCollection) {
        List<String> uniqueJobIds = jobDataCollection.getUniqueJobIds();
        uniqueJobIds.forEach(uniqueJobId -> {
            WantedJobDetail wantedJobDetail = collectWantedJobDetail(uniqueJobId);
            JobData originJobData = jobDataCollection.getJob(uniqueJobId);
            JobData jobData = wantedJobDetail.fillIn(originJobData);
            jobDataCollection.updateJobData(jobData);
        });
        jobDataCollection.extractEmptyDetail();
    }

    private WantedJobDetail collectWantedJobDetail(String uniqueJobId) {
        try {
            String jobDetailUrl = detailUrl.replace("{uniqueJobId}", uniqueJobId);
            return collectRestTemplate.getForObject(jobDetailUrl, WantedJobDetail.class);
        } catch (HttpClientErrorException clientErrorException) {
            String errorMessage = String.format("Wanted 채용공고 세부내용 수집에 실패하였습니다. :: [%d / %s]", clientErrorException.getStatusCode().value(), clientErrorException.getMessage());
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
