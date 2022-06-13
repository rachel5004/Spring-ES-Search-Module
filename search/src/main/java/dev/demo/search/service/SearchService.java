package dev.demo.search.service;

import dev.demo.search.domain.Board;
import dev.demo.search.domain.GameInfo;
import dev.demo.search.domain.GameInfo2;
import dev.demo.search.domain.SearchDto.SearchBoardResponse;
import dev.demo.search.domain.SearchDto.SearchGameResponse;
import dev.demo.search.domain.SearchDto.SearchResponse;
import dev.demo.search.repository.BoardSearchRepository;
import dev.demo.search.repository.CustomGameRepositoryImpl;
import dev.demo.search.repository.GameSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

    private static final Integer ALL_SEARCH_SIZE = 4;
    private static final Integer DEFAULT_SIZE = 20;
    private static final Integer DEFAULT_PAGE = 0;

    private final BoardSearchRepository boardSearchRepository;
    private final GameSearchRepository gameSearchRepository;
    private final CustomGameRepositoryImpl customGameRepository;

    public SearchBoardResponse searchBoardTest() {
        List<Board> searchResponse = boardSearchRepository.searchAll();
        return SearchBoardResponse.of(searchResponse);
    }

    public SearchResponse searchByKeyword(String keyword) {
        // TODO: count 쿼리와 findby.subList(0,4) 성능 비교
        // search board by keyword limit 4
        List<GameInfo> gameInfos =
                gameSearchRepository.findByKeywordforAll(keyword, getPageable(DEFAULT_PAGE, ALL_SEARCH_SIZE));
        Integer gameCount = gameSearchRepository.getTotalCount(keyword);
        // search game by keyword limit 4
        List<Board> boards =
                boardSearchRepository.findByKeywordForAll(keyword, getPageable(DEFAULT_PAGE, ALL_SEARCH_SIZE));
        Integer boardCount = boardSearchRepository.getTotalCount(keyword);
        return SearchResponse.of(gameCount, gameInfos, boardCount, boards);
    }

    public SearchGameResponse searchGameByKeyword(String keyword, String platform,
                                                  String genre, Integer page) {
        Integer totalCount = gameSearchRepository.getTotalCount(keyword);
        // get game metadata
        List<GameInfo> gameInfos = gameSearchRepository.findByKeyword(keyword, platform, genre, getPageable(page));
        // convert to info
        return SearchGameResponse.of(totalCount, gameInfos);
    }

    public SearchBoardResponse searchBoardByKeyword(String keyword, String type, Integer page) {
        List<Board> boardResults = boardSearchRepository.searchBoard(keyword, type, getPageable(page));
        // get board ids from results
        // get board entitiy from mongo
        return SearchBoardResponse.of(boardResults);
    }

    private Pageable getPageable(Integer page) {
        return PageRequest.of(page, DEFAULT_SIZE);
    }

    private Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

}