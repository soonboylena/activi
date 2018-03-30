package com.github.soonboylena.myflow.entity.config.builder;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.exceptions.ConfigBuildException;

public interface ConfigureBuilder {

    public ConfigureHolder build(String location) throws ConfigBuildException;
}
