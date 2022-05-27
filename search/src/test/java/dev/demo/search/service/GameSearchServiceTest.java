package dev.demo.search.service;

import dev.demo.search.repository.GameSearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameSearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Autowired
    private GameSearchRepository gameSearchRepository;

    @Test
    void searchByMongoTemplate () {
       customGameRepository.findGameByKeywordTest("of").stream().forEach(System.out::println);
    }

    @Test
    void searchByJpa(){
        gameSearchRepository.findByKeyword2("of").stream().forEach(System.out::println);
    }
}
