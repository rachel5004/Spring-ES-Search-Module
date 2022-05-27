package dev.demo.search.repository;

import dev.demo.search.domain.GameInfo;
import dev.demo.search.domain.GameInfo2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSearchRepository extends MongoRepository<GameInfo, String> {
    GameInfo findGameInfoById(String id);

    @Query("{$or:[{title:{$regex:/?0/}},{title_en:{$regex:/?0/}},{developer:{$regex:/?0/}}]}")
    List<GameInfo> findByKeywordforAll(String keyword, Pageable pageable);

    @Query("{'release_info.platform':/?1/,genre:/?2/,$or:[{title:{$regex:/?0/}},{title_en:{$regex:/?0/}},{developer:{$regex:/?0/}}]}")
    List<GameInfo> findByKeyword(String keyword, String platform, String genre, Pageable pageable);

    @Query("{$or:[{title:{$regex:/?0/}},{title_en:{$regex:/?0/}}]}")
    List<GameInfo2> findByKeyword2(String keyword);

    @Query(value = "{$or:[{title:{$regex:/?0/}},{title_en:{$regex:/?0/}},{developer:{$regex:/?0/}}]}", count = true)
    int getTotalCount(String keyword);
}