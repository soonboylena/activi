package com.github.soonboylena.entity.config;

import com.github.soonboylena.entity.core.MetaForm;
import com.github.soonboylena.entity.core.MetaItem;

import java.util.Map;

public interface ConfigureHolder {

    Map<String, MetaItem> getMetaItems();

    Map<String, MetaForm> getMetaForms();

    MetaForm getMetaForm(String formKey);
}
