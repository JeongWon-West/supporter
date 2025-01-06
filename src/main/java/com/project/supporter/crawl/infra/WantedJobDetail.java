package com.project.supporter.crawl.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WantedJobDetail {
    private Long id;
    private String status;
    private String dueTime;
    private JobDetail detail;
    private Company company;
    private Address address;
    private CategoryTag categoryTag;
    private List<SkillTag> skillTags;

    public JobData fillIn(JobData originJobData) {
        JobData.Job job = setupJob(originJobData);
        JobData.Detail detail = setupDetail();
        JobData.Company company = setupCompany();

        job.setDetail(detail);
        job.setCompany(company);
        originJobData.setJob(job);

        return originJobData;
    }

    private JobData.Job setupJob(JobData originJobData) {
        JobData.Job job = originJobData.getJob();
        job.setDueTime(dueTime);

        List<String> skillTagTexts = skillTags.stream()
                .map(SkillTag::getText)
                .collect(Collectors.toList());
        job.setTag(skillTagTexts);

        return job;
    }

    private JobData.Detail setupDetail() {
        JobData.Detail detail = new JobData.Detail();
        detail.setPosition(this.detail.getPosition());
        detail.setIntro(this.detail.getIntro());
        detail.setMainTasks(this.detail.getMainTasks());
        detail.setRequirements(this.detail.getRequirements());
        detail.setPreferredPoints(this.detail.getPreferredPoints());
        detail.setBenefits(this.detail.getBenefits());
        detail.setHireRounds(this.detail.getHireRounds());

        List<String> categoryTag = this.categoryTag.getChildTags().stream()
                .map(ChildTag::getText)
                .collect(Collectors.toList());
        categoryTag.add(this.categoryTag.getParentTag().getText());
        detail.setCategoryTag(categoryTag);

        return detail;
    }

    private JobData.Company setupCompany() {
        JobData.Company company = new JobData.Company();
        company.setName(this.company.getName());
        company.setAddress(this.address.getFullLocation());

        return company;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JobDetail {
        private String position;
        private String intro;
        private String mainTasks;
        private String requirements;
        private String preferredPoints;
        private String benefits;
        private String hireRounds;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Company {
        private String name;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private String fullLocation;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoryTag {
        private ParentTag parentTag;
        private List<ChildTag> childTags;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParentTag {
        private String text;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChildTag {
        private String text;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SkillTag {
        private String text;
    }
}
