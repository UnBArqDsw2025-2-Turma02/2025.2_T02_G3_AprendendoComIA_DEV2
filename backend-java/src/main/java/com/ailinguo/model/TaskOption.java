package com.ailinguo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_options")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TaskOptionId.class)
public class TaskOption {
    
    @Id
    @Column(name = "task_id")
    private Long taskId;
    
    @Id
    @Column(name = "option_index")
    private Integer optionIndex;
    
    @Column(name = "option_text", nullable = false)
    private String optionText;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;
    
    // Getters e Setters manuais
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    
    public Integer getOptionIndex() { return optionIndex; }
    public void setOptionIndex(Integer optionIndex) { this.optionIndex = optionIndex; }
    
    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }
    
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
}
