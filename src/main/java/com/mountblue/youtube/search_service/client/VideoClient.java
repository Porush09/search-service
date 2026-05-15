package com.mountblue.youtube.search_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "video-service")
public interface VideoClient {

    @GetMapping("/api/videos/thumbnail-url")
    String getThumbnailUrl(@RequestParam("key") String key);
}