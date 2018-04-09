package com.github.soonboylena.myflow.entity.core;

import com.github.soonboylena.myflow.entity.exceptions.KeyDuplicateException;
import lombok.Data;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public abstract class MetaCollection<T extends IMeta> implements IMetaInput {

    private String key;

    protected Map<String, T> metaPool = new LinkedHashMap<>();

    private String businessKey;

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public String getKey() {
        return key;
    }

    public T getMeta(String metaKey) {
        return metaPool.get(metaKey);
    }

    public Collection<T> getMetas() {
        return metaPool.values();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void addMeta(T meta) {
        if (meta == null) return;
        if (metaPool.containsKey(meta.getKey())) {
            throw new KeyDuplicateException("MetaCollection中已经包含了key为 [" + meta.getKey() + "] 的meta");
        }
        metaPool.put(meta.getKey(), meta);
    }

    public int size() {
        return metaPool.size();
    }
}
