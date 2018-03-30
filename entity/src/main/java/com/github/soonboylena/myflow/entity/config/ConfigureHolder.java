package com.github.soonboylena.myflow.entity.config;

import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.core.MetaItem;

import java.util.Map;

public interface ConfigureHolder {

    Map<String, MetaItem> getMetaItems();

    Map<String, MetaForm> getMetaForms();

    MetaForm getMetaForm(String formKey);
}
