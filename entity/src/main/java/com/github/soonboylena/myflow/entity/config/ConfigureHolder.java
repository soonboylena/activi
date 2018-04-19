package com.github.soonboylena.myflow.entity.config;

import com.github.soonboylena.myflow.entity.core.IMetaInput;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import com.github.soonboylena.myflow.entity.core.MetaList;

import java.util.Map;

public interface ConfigureHolder {

    public Map<String, IMetaInput> getMetaItems();

    public Map<String, MetaForm> getMetaForms();

    public MetaForm getMetaForm(String formKey);

}
