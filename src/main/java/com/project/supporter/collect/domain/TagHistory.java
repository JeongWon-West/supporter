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
        name = "TAG_HISTORY_SEQ_GENERATOR",
        sequenceName = "TAG_HISTORY_SEQ",
        allocationSize = 1)
public class TagHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_HISTORY_SEQ_GENERATOR")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_site_idx")
    private RecruitSite recruitSite;

    private LocalDateTime collectDate;
    private String category;

    @OneToMany(mappedBy = "tagHistory")
    private List<TagHistoryItem> items = new ArrayList<>();
}
