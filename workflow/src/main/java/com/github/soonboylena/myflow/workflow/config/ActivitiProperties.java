package com.github.soonboylena.myflow.workflow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "activiti.database")
@Component
@Data
public class ActivitiProperties {

    private String type;
    private boolean schemaUpdate = true;
    private String[] resourcePaths;
}
