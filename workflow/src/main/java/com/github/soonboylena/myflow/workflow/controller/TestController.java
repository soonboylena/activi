package com.github.soonboylena.myflow.workflow.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class TestController {

    private final static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/test")
    public Object serverInit() {
        log.info("success");

        return repositoryService.createProcessDefinitionQuery().list()
                .stream()
                .map(ProcessDefinition::getName)
                .collect(Collectors.toList());

    }
}
