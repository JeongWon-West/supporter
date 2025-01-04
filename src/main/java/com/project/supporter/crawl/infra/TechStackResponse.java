package com.project.supporter.crawl.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.supporter.crawl.domain.CollectResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TechStackResponse implements CollectResponse {

    @JsonProperty("techstacks")
    private List<TechStackData> techStacks;

    @JsonProperty("end_cursor")
    private String endCursor;

    public boolean hasNext() {
        return Objects.nonNull(endCursor);
    }

    public void merge(TechStackResponse response) {
        techStacks.addAll(response.getTechStacks());
        this.endCursor = response.getEndCursor();
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TechStackData {
        private TechStack techstack;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TechStack {
        private String id;
        private String name;
        private String category;
        private String description;
    }
}