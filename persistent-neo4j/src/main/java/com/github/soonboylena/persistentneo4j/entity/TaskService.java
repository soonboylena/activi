package com.github.soonboylena.persistentneo4j.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask() {
        Task taskInfo = new Task();
        taskInfo.setTaskName("测试");
        return taskRepository.save(taskInfo);
    }

}

