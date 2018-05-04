package com.github.soonboylena.myflow.workflow;

import com.github.soonboylena.myflow.workflow.controller.TestController;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkflowApplicationTests {
    private final static Logger log = LoggerFactory.getLogger(WorkflowApplicationTests.class);
    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void contextLoads() {

        log.info("success");

        List<String> collect = repositoryService.createProcessDefinitionQuery().list()
                .stream()
                .map(ProcessDefinition::getName)
                .collect(Collectors.toList());

        for (String s : collect) {
            System.out.println(s);
        }

    }

}
