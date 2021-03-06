package com.teamworker.services.impl;

import com.teamworker.models.Position;
import com.teamworker.models.Project;
import com.teamworker.models.User;
import com.teamworker.repositories.PositionRepository;
import com.teamworker.repositories.ProjectRepository;
import com.teamworker.repositories.UserRepository;
import com.teamworker.services.ProjectService;
import com.teamworker.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final UserService userService;

    @Override
    public Project add(Project project) {
        Project savedProject = projectRepository.save(project);
        log.info("IN add - {} project added", project.getName());

        Position position = new Position();
        position.setName("Керівник проекту");
        position.setProject(savedProject);
        Position savedPosition = positionRepository.save(position);

        User manager = userService.getById(savedProject.getManager().getId());
        manager.getPosition().add(savedPosition);
        userRepository.save(manager);

        log.info("IN add - {} position added", position.getName());
        return savedProject;
    }

    @Override
    public Project update(Long id, Project project) {
        Project foundProject = projectRepository.findById(id).orElse(null);
        if(foundProject == null) {
            return null;
        }
        foundProject.setName(project.getName());
        foundProject.setCreateTime(project.getCreateTime());
        foundProject.setProjectType(project.getProjectType());
        foundProject.setProjectStage(project.getProjectStage());

        User prevManager = userService.getById(foundProject.getManager().getId());

        Position managerPosition = foundProject.getPositions().stream()
                .filter(position -> position.getName().equals("Керівник проекту"))
                .collect(Collectors.toList()).get(0);

        prevManager.getPosition().remove(managerPosition);
        userRepository.save(prevManager);

        User newManager = userService.getById(project.getManager().getId());
        newManager.getPosition().add(managerPosition);
        User savedManager = userRepository.save(newManager);

        foundProject.setManager(savedManager);

        log.info("IN update - {} project updated", project.getId());

        return projectRepository.save(foundProject);
    }

    @Override
    public void delete(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project == null) {
            return;
        }

        List<Position> positions = project.getPositions();
        for (Position position : positions) {
            for (User user : position.getUsers()) {
                user.getPosition().remove(position);
            }
        }

        log.info("IN delete - {} project deleted", id);
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> getAll() {
        List<Project> projects = projectRepository.findAll();
        if(projects.isEmpty()) {
            return null;
        }
        log.info("IN getAll - {} projects found", projects.size());
        return projects;
    }

    @Override
    public Project getById(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project == null) {
            return null;
        }
        log.info("IN getById {} project found", project.getName());
        return project;
    }

    @Override
    public List<Project> getAllByManager(Long id) {
        List<Project> projects = projectRepository.getAllByManagerId(id);
        if(projects.isEmpty()) {
            return null;
        }
        log.info("IN getAllByManager - {} projects found", projects.size());
        return projects;
    }
}
