package com.ailinguo.repository;

import com.ailinguo.model.UserVocabularyProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserVocabularyProgressRepository extends JpaRepository<UserVocabularyProgress, Long> {
    
    List<UserVocabularyProgress> findByUserId(Long userId);
    
    Optional<UserVocabularyProgress> findByUserIdAndVocabularyWordId(Long userId, Long vocabularyWordId);
    
    @Query("SELECT uvp FROM UserVocabularyProgress uvp WHERE uvp.user.id = :userId AND uvp.masteryLevel < 5")
    List<UserVocabularyProgress> findIncompleteByUserId(@Param("userId") Long userId);
    
    @Query("SELECT uvp FROM UserVocabularyProgress uvp WHERE uvp.user.id = :userId AND uvp.masteryLevel >= 3")
    List<UserVocabularyProgress> findMasteredByUserId(@Param("userId") Long userId);
    
    @Query("SELECT SUM(uvp.xpEarned) FROM UserVocabularyProgress uvp WHERE uvp.user.id = :userId")
    Integer getTotalXpEarnedByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(uvp) FROM UserVocabularyProgress uvp WHERE uvp.user.id = :userId")
    Long getTotalWordsStudiedByUserId(@Param("userId") Long userId);
}
