package org.cecil.start.service.mapping;

import org.cecil.start.api.model.Task;
import org.cecil.start.db.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task fromEntity(TaskEntity taskEntity);

    TaskEntity toEntity(Task task);
}
