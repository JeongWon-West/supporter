package com.project.supporter.collect.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t WHERE t.name IN :tagNames")
    List<Tag> findByNames(@Param("tagNames") List<String> tagNames);

    Optional<Tag> findByName(String name);

    Set<Tag> findAllByUniqueTagIdIn(List<String> uniqueTagIds);
}
