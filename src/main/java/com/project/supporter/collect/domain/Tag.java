package com.project.supporter.collect.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "TAG_SEQ_GENERATOR",
        sequenceName = "TAG_SEQ",
        allocationSize = 1)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_SEQ_GENERATOR")
    private Long idx;

    private String uniqueTagId;
    private String category;
    private String description;
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<JobTag> jobTags = new ArrayList<>();

    @OneToMany(mappedBy = "tag")
    private List<TagHistoryItem> tagHistoryItems = new ArrayList<>();

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @CreationTimestamp
    private LocalDateTime regDate;
}
