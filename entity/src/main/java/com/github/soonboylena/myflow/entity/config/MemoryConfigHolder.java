package com.github.soonboylena.myflow.entity.config;

import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.exceptions.KeyDuplicateException;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MemoryConfigHolder implements ConfigureHolder {

    private Map<String, AbstractMetaItem> metaItems = new HashMap<>();
    private Map<String, MetaForm> metaForms = new HashMap<>();
//    private Map<String, MetaView> metaViews = new HashMap<>();

    public void addMetaItems(List<AbstractMetaItem> metaItems) {
        if (metaItems == null) {
            return;
        }

        for (AbstractMetaItem metaItem : metaItems) {

            if (this.metaItems.get(metaItem.getKey()) != null) {
                throw new KeyDuplicateException("items.item", metaItem.getKey());
            }
            this.metaItems.put(metaItem.getKey(), metaItem);
        }

    }

    public AbstractMetaItem getMetaItem(String key) {
        return metaItems.get(key);
    }

    public void addMetaForms(List<MetaForm> forms) {
        if (forms == null) return;

        for (MetaForm form : forms) {
            if (this.metaItems.get(form.getKey()) != null) {
                throw new KeyDuplicateException("items", form.getKey());
            }
            this.metaForms.put(form.getKey(), form);
        }
    }

    @Override
    public MetaForm getMetaForm(String formKey) {
        return metaForms.get(formKey);
    }

    public void addMetaForm(MetaForm o) {
        metaForms.put(o.getKey(), o);
    }
}
