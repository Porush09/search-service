package com.mountblue.youtube.search_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchVideoDto {
    private Long id;
    private String title;
    private String description;
    private String channelName;
    private String thumbnailUrl; // fresh presigned URL
    private Long viewsCount;
    private LocalDateTime createdAt;
}