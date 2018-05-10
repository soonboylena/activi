package com.github.soonboylena.myflow.workflow;

import com.github.soonboylena.myflow.springmyflow.config.CommonConfig;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class WorkflowApplicationTests {
    private final static Logger log = LoggerFactory.getLogger(WorkflowApplicationTests.class);
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private FormService formService;

    @Test
    public void contextLoads() {

        log.info("success");

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();

        for (ProcessDefinition processDefinition : list) {
            String id = processDefinition.getId();
            String tenantId = processDefinition.getTenantId();
            String deploymentId = processDefinition.getDeploymentId();
            String name = processDefinition.getName();
            int version = processDefinition.getVersion();

            String out = String.format("id: [%s],tenantId: [%s],deploymentId: [%s],name: [%s], version: [%s]", id, tenantId, deploymentId, name, version);
            System.out.println(out);

        }

    }

    @Test
    public void FormEngineTest() {

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();

        for (ProcessDefinition processDefinition : list) {
            Object renderedStartForm = formService.getRenderedStartForm(processDefinition.getId());
//            System.out.println(renderedStartForm);
        }

    }

}
