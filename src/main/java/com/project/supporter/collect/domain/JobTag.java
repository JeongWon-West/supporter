package com.project.supporter.collect.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "JOB_TAG_SEQ_GENERATOR",
        sequenceName = "JOB_TAG_SEQ",
        allocationSize = 1)
public class JobTag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_TAG_SEQ_GENERATOR")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_idx")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_idx")
    private Tag tag;
}
