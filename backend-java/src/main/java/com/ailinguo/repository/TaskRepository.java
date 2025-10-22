package com.ailinguo.repository;

import com.ailinguo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByDifficultyAndIsActiveTrue(String difficulty);
    
    List<Task> findByTypeAndIsActiveTrue(String type);
    
    List<Task> findByDifficultyAndTypeAndIsActiveTrue(String difficulty, String type);
    
    @Query("SELECT t FROM Task t WHERE t.isActive = true AND t.difficulty = :difficulty ORDER BY RANDOM() LIMIT :limit")
    List<Task> findRandomTasksByDifficulty(@Param("difficulty") String difficulty, @Param("limit") int limit);
    
    @Query("SELECT t FROM Task t WHERE t.isActive = true AND t.difficulty IN :difficulties ORDER BY RANDOM() LIMIT :limit")
    List<Task> findRandomTasksByDifficulties(@Param("difficulties") List<String> difficulties, @Param("limit") int limit);
    
    long countByDifficultyAndIsActiveTrue(String difficulty);
    
    long countByTypeAndIsActiveTrue(String type);
    
    // Métodos para estatísticas
    long countByIsActiveTrue();
}
