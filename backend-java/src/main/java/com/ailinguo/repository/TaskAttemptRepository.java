package com.ailinguo.repository;

import com.ailinguo.model.TaskAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskAttemptRepository extends JpaRepository<TaskAttempt, Long> {
    
    List<TaskAttempt> findByUserId(Long userId);
    
    List<TaskAttempt> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);
    
    List<TaskAttempt> findByUserIdAndIsCorrectTrue(Long userId);
    
    List<TaskAttempt> findByUserIdAndIsCorrectFalse(Long userId);
    
    long countByUserId(Long userId);
    
    long countByUserIdAndIsCorrectTrue(Long userId);
    
    long countByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);
    
    @Query("SELECT ta FROM TaskAttempt ta WHERE ta.user.id = :userId AND ta.createdAt >= :startDate AND ta.createdAt <= :endDate")
    List<TaskAttempt> findByUserIdAndDateRange(@Param("userId") Long userId, 
                                               @Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ta.task.id, COUNT(ta) FROM TaskAttempt ta WHERE ta.user.id = :userId GROUP BY ta.task.id")
    List<Object[]> getTaskAttemptCountsByUser(@Param("userId") Long userId);
    
    @Query("SELECT ta FROM TaskAttempt ta WHERE ta.user.id = :userId AND ta.task.id = :taskId")
    List<TaskAttempt> findByUserIdAndTaskId(@Param("userId") Long userId, @Param("taskId") Long taskId);
    
    // Métodos para estatísticas
    long countByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    long countByCreatedAtAfter(LocalDateTime date);
}
