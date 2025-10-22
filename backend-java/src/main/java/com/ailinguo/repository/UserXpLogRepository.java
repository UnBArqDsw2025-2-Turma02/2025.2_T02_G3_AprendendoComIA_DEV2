package com.ailinguo.repository;

import com.ailinguo.model.UserXpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserXpLogRepository extends JpaRepository<UserXpLog, Long> {
    
    List<UserXpLog> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT uxl FROM UserXpLog uxl WHERE uxl.user.id = :userId AND uxl.createdAt >= :since")
    List<UserXpLog> findByUserIdAndCreatedAtAfter(@Param("userId") Long userId, @Param("since") LocalDateTime since);
    
    @Query("SELECT SUM(uxl.xpChange) FROM UserXpLog uxl WHERE uxl.user.id = :userId")
    Integer getTotalXpChangeByUserId(@Param("userId") Long userId);
    
    @Query("SELECT uxl FROM UserXpLog uxl WHERE uxl.user.id = :userId AND uxl.sourceType = :sourceType")
    List<UserXpLog> findByUserIdAndSourceType(@Param("userId") Long userId, @Param("sourceType") String sourceType);
}
