package com.project.supporter.collect.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
        name = "COMPANY_SEQ_GENERATOR",
        sequenceName = "COMPANY_SEQ",
        allocationSize = 1)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ_GENERATOR")
    private Long idx;

    private String name;

    private String address;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs = new ArrayList<>();

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @CreationTimestamp
    private LocalDateTime regDate;

    @Builder
    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
