package com.project.supporter.collect.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "JOB_SEQ_GENERATOR",
        sequenceName = "JOB_SEQ",
        allocationSize = 1)
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_SEQ_GENERATOR")
    private Long idx;

    private String uniqueJobId;
    private LocalDateTime dueTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_site_idx")
    private RecruitSite recruitSite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_idx")
    private Company company;

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    private JobDetail jobDetail;

    @OneToMany(mappedBy = "job")
    private List<JobTag> jobTags = new ArrayList<>();
}
