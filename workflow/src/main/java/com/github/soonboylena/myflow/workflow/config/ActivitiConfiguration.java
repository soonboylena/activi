package com.github.soonboylena.myflow.workflow.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ActivitiConfiguration {

    @Autowired
    private ActivitiProperties properties;

    private final ResourceLoader defaultResourceLoader = new DefaultResourceLoader();

    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);

        processEngineConfiguration.setDatabaseSchemaUpdate(String.valueOf(properties.isSchemaUpdate()));
        processEngineConfiguration.setDatabaseType(properties.getType());

        String[] resourcePaths = properties.getResourcePaths();

        Resource[] resources = readResources(resourcePaths);


        processEngineConfiguration.setDeploymentResources(resources);

        processEngineConfiguration.setTransactionManager(transactionManager);

        return processEngineConfiguration;
    }

    private Resource[] readResources(String[] resourcePaths) {

        List<Resource> resourceList = new ArrayList<>();

        String process0 = "classpath:/processes/*";

        Resource[] read = readResource(process0);

        if (read != null) {
            resourceList.addAll(Arrays.asList(read));
        }

        if (resourcePaths != null) {
            for (String resourcePath : resourcePaths) {
                Resource[] read2 = readResource(resourcePath);
                if (read2 != null) {
                    resourceList.addAll(Arrays.asList(read2));
                }
            }

        }
        return resourceList.toArray(new Resource[]{});
    }

    private Resource[] readResource(String process0) {
        try {
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(defaultResourceLoader).getResources(process0);
            return resources;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //流程引擎，与spring整合使用factoryBean
    @Bean
    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        return processEngineFactoryBean;
    }

    //八大接口
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine) {
        return processEngine.getDynamicBpmnService();
    }

}
