package com.ailinguo.repository;

import com.ailinguo.model.TaskOption;
import com.ailinguo.model.TaskOptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskOptionRepository extends JpaRepository<TaskOption, TaskOptionId> {
    
    List<TaskOption> findByTaskIdOrderByOptionIndex(Long taskId);
    
    @Query("SELECT to FROM TaskOption to WHERE to.taskId = :taskId ORDER BY to.optionIndex")
    List<TaskOption> findOptionsByTaskId(@Param("taskId") Long taskId);
    
    void deleteByTaskId(Long taskId);
}
