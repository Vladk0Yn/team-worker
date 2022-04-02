package com.teamworker.models;

import com.teamworker.models.enums.Priority;
import com.teamworker.models.enums.TaskStage;
import com.teamworker.models.enums.TaskType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Task {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name")
    private String name;

    private String description;

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "due_time")
    private Date dueTime;

    @LastModifiedDate
    @Column(name = "last_edit_time")
    private Date lastEditTime;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id",nullable = false)
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id",nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id",nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    private TaskStage stage;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskType type;
}
