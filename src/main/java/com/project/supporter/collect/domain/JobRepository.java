package com.project.supporter.collect.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j.uniqueJobId FROM Job j " +
            "JOIN j.recruitSite rs " +
            "WHERE rs.name = :recruitSiteName " +
            "AND j.uniqueJobId IN :uniqueJobIds")
    List<String> findExistingUniqueJobIds(
            @Param("recruitSiteName") String recruitSiteName,
            @Param("uniqueJobIds") List<String> uniqueJobIds
    );
}
