package com.mountblue.youtube.search_service.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "videos")
public class VideoSearchDocument {

    @Id
    private Long id;

    private String title;

    private String description;

    private String channelName;

    private String thumbnailKey;

    private Long viewsCount;

    private LocalDateTime createdAt;
}