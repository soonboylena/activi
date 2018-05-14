package com.github.soonboylena.myflow.workflow.config;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.framework.web.FormQueryService;
import com.github.soonboylena.myflow.workflow.mflConfig.MflFormEngine;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.autodeployment.ResourceParentFolderAutoDeploymentStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.Collections;
import java.util.List;

@Configuration
public class ActivitiConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiConfiguration.class);

    @Autowired
    private ActivitiProperties properties;

    @Autowired
    private ConfigureHolder configureHolder;

    @Autowired
    private FormQueryService formQueryService;

    private final ResourceLoader defaultResourceLoader = new DefaultResourceLoader();


    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(@Qualifier("dataSource") DataSource dataSource, PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);

        processEngineConfiguration.setDatabaseSchemaUpdate(String.valueOf(properties.isSchemaUpdate()));

        String[] resourcePaths = properties.getResourcePaths();
        Resource[] resources = readResources(resourcePaths);

        if (logger.isInfoEnabled()) {
            logger.info(" -- 加载的Activiti流程文件:");
            for (Resource resource : resources) {
                logger.info("  - {}", resource.getDescription());
            }
        }
        processEngineConfiguration.setDeploymentResources(resources);
        processEngineConfiguration.setTransactionManager(transactionManager);
//        processEngineConfiguration.setDeploymentMode(ResourceParentFolderAutoDeploymentStrategy.DEPLOYMENT_MODE);

        processEngineConfiguration.setCustomFormEngines(Collections.singletonList(new MflFormEngine(configureHolder, formQueryService)));

        return processEngineConfiguration;
    }

    private Resource[] readResources(String[] resourcePaths) {

        List<Resource> resourceList = new ArrayList<>();

        // 默认路径
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
        return resourceList.toArray(new Resource[resourceList.size()]);
    }

    private Resource[] readResource(String process0) {
        try {
            return ResourcePatternUtils.getResourcePatternResolver(defaultResourceLoader).getResources(process0);
        } catch (IOException e) {
            logger.warn("读取配置指定的资源文件时发生IO错误。", e);
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
