package com.project.supporter.collect.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findAllByUniqueTagIdIn(List<String> uniqueTagIds);
}
