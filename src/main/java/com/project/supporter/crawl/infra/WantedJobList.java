package com.project.supporter.crawl.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WantedJobList {

    private List<JobSimpleDto> data;
    private Links links;

    public JobDataCollection convert(String recruitSite) {
        List<JobData> jobDataList = data.stream()
                .map(dto -> JobData.init(recruitSite, dto.getId().toString()))
                .collect(Collectors.toList());

        return JobDataCollection.init(jobDataList, links.getNext());
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JobSimpleDto {
        private Long id;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        private String next;
    }
}
