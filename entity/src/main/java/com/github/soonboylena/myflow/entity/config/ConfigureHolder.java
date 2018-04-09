package com.github.soonboylena.myflow.entity.config;

import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import com.github.soonboylena.myflow.entity.core.MetaView;

import java.util.Map;

public interface ConfigureHolder {

    public Map<String, AbstractMetaItem> getMetaItems();

    public Map<String, MetaForm> getMetaForms();

    public MetaForm getMetaForm(String formKey);

    public MetaView getMetaView(String viewKey);
}
