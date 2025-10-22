package com.ailinguo.repository;

import com.ailinguo.model.VocabularyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyWordRepository extends JpaRepository<VocabularyWord, Long> {
    
    List<VocabularyWord> findByCefrLevel(String cefrLevel);
    
    List<VocabularyWord> findByDifficulty(String difficulty);
    
    List<VocabularyWord> findByCategoryId(Long categoryId);
    
    @Query("SELECT v FROM VocabularyWord v WHERE v.cefrLevel = :cefrLevel AND v.difficulty = :difficulty")
    List<VocabularyWord> findByCefrLevelAndDifficulty(@Param("cefrLevel") String cefrLevel, @Param("difficulty") String difficulty);
    
    @Query("SELECT v FROM VocabularyWord v WHERE v.cefrLevel = :cefrLevel ORDER BY RANDOM() LIMIT :limit")
    List<VocabularyWord> findRandomByCefrLevel(@Param("cefrLevel") String cefrLevel, @Param("limit") int limit);
    
    @Query("SELECT v FROM VocabularyWord v WHERE v.category.id = :categoryId ORDER BY RANDOM() LIMIT :limit")
    List<VocabularyWord> findRandomByCategory(@Param("categoryId") Long categoryId, @Param("limit") int limit);
}
