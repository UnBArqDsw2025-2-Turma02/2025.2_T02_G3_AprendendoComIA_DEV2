package com.ailinguo.repository;

import com.ailinguo.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    
    List<Exercise> findByCefrLevel(String cefrLevel);
    
    List<Exercise> findByDifficulty(String difficulty);
    
    List<Exercise> findByType(String type);
    
    @Query("SELECT e FROM Exercise e WHERE e.cefrLevel = :cefrLevel AND e.difficulty = :difficulty")
    List<Exercise> findByCefrLevelAndDifficulty(@Param("cefrLevel") String cefrLevel, @Param("difficulty") String difficulty);
    
    @Query("SELECT e FROM Exercise e WHERE e.cefrLevel = :cefrLevel ORDER BY RANDOM() LIMIT :limit")
    List<Exercise> findRandomByCefrLevel(@Param("cefrLevel") String cefrLevel, @Param("limit") int limit);
}
