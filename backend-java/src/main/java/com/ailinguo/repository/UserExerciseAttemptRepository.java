package com.ailinguo.repository;

import com.ailinguo.model.UserExerciseAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserExerciseAttemptRepository extends JpaRepository<UserExerciseAttempt, Long> {
    
    List<UserExerciseAttempt> findByUserIdOrderByCompletedAtDesc(Long userId);
    
    List<UserExerciseAttempt> findByUserIdAndExerciseId(Long userId, Long exerciseId);
    
    @Query("SELECT uea FROM UserExerciseAttempt uea WHERE uea.user.id = :userId AND uea.completedAt >= :since")
    List<UserExerciseAttempt> findByUserIdAndCompletedAtAfter(@Param("userId") Long userId, @Param("since") LocalDateTime since);
    
    @Query("SELECT SUM(uea.xpEarned) FROM UserExerciseAttempt uea WHERE uea.user.id = :userId")
    Integer getTotalXpEarnedByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(uea) FROM UserExerciseAttempt uea WHERE uea.user.id = :userId")
    Long getTotalAttemptsByUserId(@Param("userId") Long userId);
}
