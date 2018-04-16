package com.github.soonboylena.myflow.entity.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListEntity implements IEntity {

    private MetaForm meta;
    private List<Map<String, Object>> data = new ArrayList<>();


    @Override
    public IMeta acquireMeta() {
        return meta;
    }

    @Override
    public List<Map<String, Object>> getData() {
        return data;
    }
}
