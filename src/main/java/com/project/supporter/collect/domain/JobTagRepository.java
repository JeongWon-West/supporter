package com.project.supporter.collect.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {
    void deleteByJobIdx(Long jobIdx);
}
