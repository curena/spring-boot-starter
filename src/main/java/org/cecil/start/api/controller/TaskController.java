package org.cecil.start.api.controller;

import lombok.RequiredArgsConstructor;
import org.cecil.start.api.model.Task;
import org.cecil.start.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.addTask(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task.getDescription()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
