package dev.demo.search.service;

import dev.demo.search.domain.Board;
import dev.demo.search.domain.GameInfo;
import dev.demo.search.domain.GameInfo2;
import dev.demo.search.domain.SearchDto.*;
import dev.demo.search.repository.BoardSearchRepository;
import dev.demo.search.repository.CustomGameRepositoryImpl;
import dev.demo.search.repository.GameSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

    private static final int ALL_SEARCH_DEFAULT_SIZE = 4;
    private static final int DEFAULT_SIZE = 20;
    private static final int DEFAULT_PAGE = 0;

    private final BoardSearchRepository boardSearchRepository;
    private final GameSearchRepository gameSearchRepository;
    private final CustomGameRepositoryImpl customGameRepository;

    public SearchBoardResponse searchTest() {
        List<Board> searchResponse = boardSearchRepository.searchTest();
        return SearchBoardResponse.of(searchResponse);
    }

    public List<GameInfo2> searchgameTest(String keyword) {
//        List<GameInfo2> searchResponse = gameSearchRepository.findByKeyword2(keyword);
        List<GameInfo2> searchResponse = customGameRepository.findGameByKeywordTest(keyword);
        return searchResponse;
    }


    public SearchResponse searchByKeyword(String keyword) {
        // search game by keyword limit 4
        List<Board> boards =
                boardSearchRepository.findByKeywordForAll(keyword, getPageable(DEFAULT_PAGE, ALL_SEARCH_DEFAULT_SIZE));
//        int boardCount = boardSearchRepository.getTotalCount(keyword);
        // search board by keyword limit 6
        List<GameInfo> gameInfos =
                gameSearchRepository.findByKeywordforAll(keyword, getPageable(DEFAULT_PAGE, ALL_SEARCH_DEFAULT_SIZE));
        int gameCount = gameSearchRepository.getTotalCount(keyword);
        // searchresponse.of(games, boards)
        return SearchResponse.of(1, gameInfos,boards);
    }

    public SearchGameResponse searchGameByKeyword(String keyword, String platform,
                                                  String genre, int page) {
        int totalCount = gameSearchRepository.getTotalCount(keyword);
        // get game metadata
        List<GameInfo> gameInfos = gameSearchRepository.findByKeyword(keyword, platform, genre, getPageable(page));
        System.out.println(gameInfos.size());
        // convert to info
        return SearchGameResponse.of(totalCount, gameInfos);
    }

    public SearchBoardResponse searchBoardByKeyword(String keyword, String type, int page) {
        List<Board> boardResults = boardSearchRepository.searchBoard(keyword, type, getPageable(page));
        // get board ids from results
        // get board entitiy from mongo
        return SearchBoardResponse.of(boardResults);
    }

    private Pageable getPageable(int page) {
        return PageRequest.of(page, DEFAULT_SIZE);
    }
    private Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

}