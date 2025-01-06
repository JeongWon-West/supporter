package com.project.supporter.crawl.infra;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class JobDataCollection {

    private List<JobData> jobDataList;

    private String nextCursor;

    public boolean hasNext() {
        return Objects.nonNull(nextCursor);
    }

    public static JobDataCollection init(List<JobData> jobDataList, String nextCursor) {
        JobDataCollection jobDataCollection = new JobDataCollection();
        jobDataCollection.addAll(jobDataList);
        jobDataCollection.setNextCursor(nextCursor);
        return jobDataCollection;
    }

    public void add(JobData jobData) {
        jobDataList.add(jobData);
    }

    private void addAll(List<JobData> jobDataList) {
        if (Objects.isNull(jobDataList)) {
            this.jobDataList = new ArrayList<>();
        }
        this.jobDataList.addAll(jobDataList);
    }

    private void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

    public List<String> getUniqueJobIds() {
        return jobDataList.stream()
                .map(JobData::getJob)
                .map(JobData.Job::getUniqueJobId)
                .collect(Collectors.toList());
    }

    public void updateJobData(JobData jobData) {
        jobDataList.stream()
                .filter(data -> data.getJob().getUniqueJobId().equals(jobData.getJob().getUniqueJobId()))
                .findFirst()
                .ifPresent(data -> data.setJob(jobData.getJob()));
    }

    public void extractEmptyDetail() {
        jobDataList.removeIf(JobData::isEmptyDetail);
    }

    public JobData getJob(String uniqueJobId) {
        return jobDataList.stream()
                .filter(data -> data.getJob().getUniqueJobId().equals(uniqueJobId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No job data"));
    }

    public void clearJobData() {
        jobDataList.clear();
    }

    public void extractExistJobData(List<String> existingUniqueJobIds) {
        jobDataList.removeIf(data -> existingUniqueJobIds.contains(data.getJob().getUniqueJobId()));
    }

    public boolean isEmpty() {
        return jobDataList.isEmpty();
    }
}
