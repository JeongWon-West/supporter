package com.project.supporter.collect.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "JOB_DETAIL_SEQ_GENERATOR",
        sequenceName = "JOB_DETAIL_SEQ",
        allocationSize = 1)
public class JobDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_DETAIL_SEQ_GENERATOR")
    private Long idx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_idx")
    private Job job;

    private String position;
    private String intro;

    @Lob
    private String mainTasks;

    @Lob
    private String requirements;

    @Lob
    private String preferredPoints;

    @Lob
    private String benefits;

    private String hireRounds;
}