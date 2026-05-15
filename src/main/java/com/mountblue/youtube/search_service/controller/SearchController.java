package com.mountblue.youtube.search_service.controller;

import com.mountblue.youtube.search_service.dto.SearchVideoDto;
import com.mountblue.youtube.search_service.dto.VideoIndexRequest;
import com.mountblue.youtube.search_service.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/videos")
    public List<SearchVideoDto> searchVideos(
            @RequestParam String q) {

        return searchService.searchVideos(q);
    }

    @PostMapping("/videos")
    public String indexVideo(@RequestBody VideoIndexRequest request) {
        searchService.indexVideo(request);
        return "Video indexed successfully";
    }
}