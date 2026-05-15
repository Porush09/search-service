package com.mountblue.youtube.search_service.repository;

import com.mountblue.youtube.search_service.document.VideoSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoSearchRepository
        extends ElasticsearchRepository<VideoSearchDocument, Long> {
}