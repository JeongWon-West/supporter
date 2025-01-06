package com.project.supporter.collect.application;

import com.project.supporter.collect.domain.Job;
import com.project.supporter.collect.domain.JobRepository;
import com.project.supporter.crawl.infra.JobData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final TagMappingService tagMappingService;

    public List<String> findExistingUniqueJobIds(String recruitSiteName, List<String> uniqueJobIds) {
        return jobRepository.findExistingUniqueJobIds(recruitSiteName, uniqueJobIds);
    }

    @Transactional
    public void saveJobData(List<JobData> jobs) {
        Map<String, List<String>> jobTagMap = jobs.stream()
                .map(JobData::getJob)
                .filter(job -> Objects.nonNull(job.getUniqueJobId()))
                .collect(Collectors.toMap(
                        JobData.Job::getUniqueJobId,
                        JobData.Job::getTag,
                        (existing, replacement) -> existing  // 중복 키가 있을 경우 기존 값 유지
                ));
        List<Job> jobEntities = jobs.stream()
                .map(JobData::convertToJob)
                .collect(Collectors.toList());
        jobEntities = jobRepository.saveAll(jobEntities);

        jobEntities.forEach(job -> tagMappingService.mapAndSaveJobTags(job, jobTagMap.get(job.getUniqueJobId())));
    }
}
