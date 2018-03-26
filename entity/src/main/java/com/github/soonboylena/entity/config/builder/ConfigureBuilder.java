package com.github.soonboylena.entity.config.builder;

import com.github.soonboylena.entity.config.ConfigureHolder;
import com.github.soonboylena.entity.exceptions.ConfigBuildException;

public interface ConfigureBuilder {

    public ConfigureHolder build(String location) throws ConfigBuildException;
}
