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
        name = "RECRUIT_SITE_SEQ_GENERATOR",
        sequenceName = "RECRUIT_SITE_SEQ",
        allocationSize = 1)
public class RecruitSite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECRUIT_SITE_SEQ_GENERATOR")
    private Long idx;

    private String name;
    private LocalDateTime collectDate;
    private int recruitNumber;

    @OneToMany(mappedBy = "recruitSite")
    private List<Job> jobs = new ArrayList<>();

    @OneToMany(mappedBy = "recruitSite")
    private List<TagHistory> tagHistories = new ArrayList<>();
}
