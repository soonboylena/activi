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
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.github.soonboylena")
@Import(CommonConfig.class)
public class ProcessServiceTest {

    @Autowired
    ProcessService service;

    @Test
    public void myTask() {
        List<Map<String, Object>> tasks = service.myTask("levelUser2");
        for (Map<String, Object> task : tasks) {
            System.out.println(task);
        }
    }
}