package com.teamworker.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamworker.models.Project;
import com.teamworker.models.Task;
import com.teamworker.models.enums.Priority;
import com.teamworker.models.enums.TaskStage;
import com.teamworker.models.enums.TaskType;
import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private String createTime;
    private String dueTime;
    private String lastEditTime;
    private String startTime;
    private String endTime;
    private UserDto assignee;
    private UserDto creator;
    private Project project;
    private Priority priority;
    private TaskStage stage;
    private TaskType type;
    private boolean isOverdue;

    public Task toTask() throws ParseException {

        SimpleDateFormat getDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        Date parseCreateTimeDate = simpleDateFormat.parse(createTime);
        Timestamp parsedCreateTime = new java.sql.Timestamp(parseCreateTimeDate.getTime());

        String dueTimeReplaced = dueTime.replace('T', ' ');
        Timestamp parsedGetDueTime = new Timestamp(getDateFormat.parse(dueTimeReplaced).getTime());

        Date parseLastEditTimeDate = simpleDateFormat.parse(lastEditTime);
        Timestamp parsedLastEditTime = new java.sql.Timestamp(parseLastEditTimeDate.getTime());

        Date parseStartTimeDate = simpleDateFormat.parse(startTime);
        Timestamp parsedStartTime = new java.sql.Timestamp(parseStartTimeDate.getTime());

        Date parseEndTimeDate = simpleDateFormat.parse(endTime);
        Timestamp parsedEndTime = new java.sql.Timestamp(parseEndTimeDate.getTime());

        Task task = new Task();

        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setCreateTime(parsedCreateTime);
        task.setDueTime(parsedGetDueTime);
        task.setLastEditTime(parsedLastEditTime);
        task.setStartTime(parsedStartTime);
        task.setEndTime(parsedEndTime);
        task.setAssignee(assignee.toUser());
        task.setCreator(creator.toUser());
        task.setProject(project);
        task.setPriority(priority);
        task.setStage(stage);
        task.setType(type);
        task.setOverdue(isOverdue);
        return task;
    }

    public static TaskDto fromTask(Task task) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setCreateTime(simpleDateFormat.parse(task.getCreateTime().toString()).toString());
        taskDto.setDueTime(simpleDateFormat.parse(task.getDueTime().toString()).toString());
        taskDto.setLastEditTime(simpleDateFormat.parse(task.getLastEditTime().toString()).toString());
        taskDto.setStartTime(simpleDateFormat.parse(task.getStartTime().toString()).toString());
        taskDto.setEndTime(simpleDateFormat.parse(task.getEndTime().toString()).toString());
        taskDto.setAssignee(UserDto.fromUser(task.getAssignee()));
        taskDto.setCreator(UserDto.fromUser(task.getCreator()));
        taskDto.setProject(task.getProject());
        taskDto.setPriority(task.getPriority());
        taskDto.setStage(task.getStage());
        taskDto.setType(task.getType());
        taskDto.setOverdue(task.isOverdue());
        return taskDto;
    }
}
