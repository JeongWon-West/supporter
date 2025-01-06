package com.project.supporter.crawl.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.supporter.collect.domain.Company;
import com.project.supporter.collect.domain.JobDetail;
import com.project.supporter.crawl.domain.CollectResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobData implements CollectResponse {
    private String recruitSite;
    private LocalDateTime collectDate;
    private Job job;

    public static JobData init(String recruitSite, String uniqueJobId) {
        JobData jobData = new JobData();
        jobData.setRecruitSite(recruitSite);
        jobData.setCollectDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        Job job = new Job();
        job.setUniqueJobId(uniqueJobId);
        jobData.setJob(job);
        return jobData;
    }

    public boolean isEmptyDetail() {
        return Objects.isNull(job.getDetail());
    }

    public com.project.supporter.collect.domain.Job convertToJob() {
        JobDetail detail = JobDetail.builder()
                .position(job.getDetail().getPosition())
                .intro(job.getDetail().getIntro())
                .mainTasks(job.getDetail().getMainTasks())
                .requirements(job.getDetail().getRequirements())
                .preferredPoints(job.getDetail().getPreferredPoints())
                .benefits(job.getDetail().getBenefits())
                .hireRounds(job.getDetail().getHireRounds())
                .build();

        com.project.supporter.collect.domain.Company company = com.project.supporter.collect.domain.Company.builder()
                .name(job.getCompany().getName())
                .address(job.getCompany().getAddress())
                .build();

        return com.project.supporter.collect.domain.Job.builder()
                .uniqueJobId(job.getUniqueJobId())
                .collectDate(collectDate)
                .dueTime(LocalDateTime.parse(job.getDueTime()))
                .jobDetail(detail)
                .company(company)
                .build();
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Job {
        private String uniqueJobId;
        private String dueTime;
        private Detail detail;
        private Company company;
        private List<String> tag;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Detail {
        private String position;
        private String intro;
        private String mainTasks;
        private String requirements;
        private String preferredPoints;
        private String benefits;
        private String hireRounds;
        private List<String> categoryTag;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Company {
        private String name;
        private String address;
    }
}
