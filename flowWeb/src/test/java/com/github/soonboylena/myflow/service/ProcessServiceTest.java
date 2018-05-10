package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.springmyflow.config.CommonConfig;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.github.soonboylena")
@Import(CommonConfig.class)
public class ProcessServiceTest {

    @Autowired
    ProcessService service;

    @Test
    public void myTask() {
        List<Task> tasks = service.myTask("levelUser1");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}