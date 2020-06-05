package org.cecil.start.service;

import lombok.RequiredArgsConstructor;
import org.cecil.start.api.model.Task;
import org.cecil.start.db.entity.TaskEntity;
import org.cecil.start.db.repository.TaskRepository;
import org.cecil.start.service.mapping.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public String addTask(Task task) {
        TaskEntity taskEntity = taskRepository.save(taskMapper.toEntity(task));
        return String.valueOf(taskEntity.getId());
    }

    public List<Task> getTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public String updateTask(long id, String updatedDescription) {
        TaskEntity existingTask = taskRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        existingTask.setDescription(updatedDescription);
        return taskMapper.fromEntity(existingTask).getDescription();
    }

    public void deleteTask(long id) {
        TaskEntity existingTask = taskRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        taskRepository.delete(existingTask);
    }
}
