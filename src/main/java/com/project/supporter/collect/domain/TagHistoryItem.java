package com.project.supporter.collect.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "TAG_HISTORY_ITEM_SEQ_GENERATOR",
        sequenceName = "TAG_HISTORY_ITEM_SEQ",
        allocationSize = 1)
public class TagHistoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_HISTORY_ITEM_SEQ_GENERATOR")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_history_idx")
    private TagHistory tagHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_idx")
    private Tag tag;

    private int rank;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @CreationTimestamp
    private LocalDateTime regDate;
}
