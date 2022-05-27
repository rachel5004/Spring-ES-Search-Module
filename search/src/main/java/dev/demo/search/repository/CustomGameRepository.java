package dev.demo.search.repository;

import dev.demo.search.domain.GameInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomGameRepository {
    List<GameInfo> findGameByKeyword(String keyword);
}
