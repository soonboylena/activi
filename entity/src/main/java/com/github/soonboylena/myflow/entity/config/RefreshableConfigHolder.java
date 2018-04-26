package com.github.soonboylena.myflow.entity.config;

import com.github.soonboylena.myflow.entity.core.IMetaInput;
import com.github.soonboylena.myflow.entity.core.MetaForm;

import java.util.Map;

/**
 * 给真正的holder加一个容器，以便实现热更新
 */
public class RefreshableConfigHolder implements ConfigureHolder, ConfigRefreshable {

    private ConfigureHolder holder;

    public RefreshableConfigHolder(ConfigureHolder holder) {
        this.holder = holder;
    }

    @Override
    public Map<String, IMetaInput> getMetaItems() {
        return holder.getMetaItems();
    }

    @Override
    public Map<String, MetaForm> getMetaForms() {
        return holder.getMetaForms();
    }

    @Override
    public MetaForm getMetaForm(String formKey) {
        return holder.getMetaForm(formKey);
    }

    @Override
    public void refresh(ConfigureHolder newConfigHolder) {
        this.holder = newConfigHolder;
    }
}
