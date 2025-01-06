package com.project.supporter.collect.application;

import com.project.supporter.collect.domain.RecruitSite;
import com.project.supporter.collect.domain.RecruitSiteRepository;
import com.project.supporter.collect.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitSiteRepository recruitSiteRepository;

    public List<RecruitSite> getRecruitSitesByStatus(Status status) {
        return recruitSiteRepository.findAllByStatus(status);
    }
}
