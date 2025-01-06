package com.project.supporter.collect.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitSiteRepository extends JpaRepository<RecruitSite, Long> {

    List<RecruitSite> findAllByStatus(Status status);
}
