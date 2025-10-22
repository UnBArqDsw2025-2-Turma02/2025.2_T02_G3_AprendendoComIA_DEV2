package com.ailinguo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskOptionId implements Serializable {
    private Long taskId;
    private Integer optionIndex;
}
