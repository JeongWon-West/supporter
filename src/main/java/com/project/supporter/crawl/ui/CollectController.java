package com.project.supporter.crawl.ui;

import com.project.supporter.common.api.ApiResponse;
import com.project.supporter.crawl.application.CrawlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/collect")
@RequiredArgsConstructor
public class CollectController {

    private final CrawlService crawlService;

    @PostMapping("/tag")
    public ResponseEntity<ApiResponse<?>> collectTags() {
        try {
            crawlService.collectTag();
            return ResponseEntity.ok(ApiResponse.ok());
        } catch (Exception e) {
            ApiResponse<Object> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
