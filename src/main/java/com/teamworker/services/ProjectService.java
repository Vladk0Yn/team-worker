package com.teamworker.services;

import com.teamworker.models.Project;
import com.teamworker.models.User;

import java.util.List;

public interface ProjectService {

    Project add(Project project);

    Project update(Long id, Project project);

    void delete(Long id);

    List<Project> getAll();

    Project getById(Long id);

    List<Project> getAllByManager(Long id);
}
