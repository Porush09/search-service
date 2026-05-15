package com.mountblue.youtube.search_service.service;

import com.mountblue.youtube.search_service.client.VideoClient;
import com.mountblue.youtube.search_service.document.VideoSearchDocument;
import com.mountblue.youtube.search_service.dto.SearchVideoDto;
import com.mountblue.youtube.search_service.dto.VideoIndexRequest;
import com.mountblue.youtube.search_service.repository.VideoSearchRepository;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.multiMatch;

@Service
public class SearchService {

    private final VideoSearchRepository videoSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final VideoClient videoClient;

    public SearchService(VideoSearchRepository videoSearchRepository,
                         ElasticsearchOperations elasticsearchOperations,
                         VideoClient videoClient) {
        this.videoSearchRepository = videoSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.videoClient = videoClient;
    }

    public List<SearchVideoDto> searchVideos(String query) {

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(multiMatch(m -> m
                        .query(query)
                        .fields("title", "description", "channelName")
                ))
                .build();

        SearchHits<VideoSearchDocument> hits =
                elasticsearchOperations.search(searchQuery, VideoSearchDocument.class);

        return hits.getSearchHits()
                .stream()
                .map(hit -> {
                    VideoSearchDocument doc = hit.getContent();

                    SearchVideoDto dto = new SearchVideoDto();
                    dto.setId(doc.getId());
                    dto.setTitle(doc.getTitle());
                    dto.setDescription(doc.getDescription());
                    dto.setChannelName(doc.getChannelName());
                    dto.setViewsCount(doc.getViewsCount());
                    dto.setCreatedAt(doc.getCreatedAt());

                    String freshThumbnailUrl =
                            videoClient.getThumbnailUrl(doc.getThumbnailKey());

                    dto.setThumbnailUrl(freshThumbnailUrl);

                    return dto;
                })
                .toList();
    }

    public void indexVideo(VideoIndexRequest request) {

        VideoSearchDocument doc = new VideoSearchDocument();

        doc.setId(request.getId());
        doc.setTitle(request.getTitle());
        doc.setDescription(request.getDescription());
        doc.setChannelName(request.getChannelName());
        doc.setThumbnailKey(request.getThumbnailKey());
        doc.setViewsCount(request.getViewsCount());
        doc.setCreatedAt(request.getCreatedAt());

        videoSearchRepository.save(doc);
    }
}