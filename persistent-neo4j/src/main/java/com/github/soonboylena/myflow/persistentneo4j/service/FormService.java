package com.github.soonboylena.myflow.persistentneo4j.service;

import com.github.soonboylena.myflow.entity.core.*;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.FormGraphRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormService {

    @Autowired
    private FormGraphRepository repository;

    public DynamicEntity save(IEntity iEntity) {

        DynamicEntity entity = resolve(iEntity);
        return repository.save(entity);
    }

    /**
     * 根据类型分类，走不同的插入流程
     *
     * @param iEntity
     * @return
     */
    private DynamicEntity resolve(IEntity iEntity) {

        if (iEntity instanceof ViewEntity) {
            ViewEntity view = (ViewEntity) iEntity;
            return resolveAsView(view);
        }

        if (iEntity instanceof FormEntity) {

            FormEntity form = (FormEntity) iEntity;
            return resolveAsForm(form);
        }

        throw new RuntimeException("不是Entity类型: " + iEntity.getClass().getName());

    }

    private DynamicEntity resolveAsForm(FormEntity form) {

        MetaForm meta = form.getMeta();
        List<FieldEntity> data = form.getFieldEntities();
        Optional<String> businessName = findBusinessName(meta, data);
        DynamicEntity dynamic = new DynamicEntity(businessName.orElse(meta.getCaption()), meta.getKey());
        for (IEntity datum : data) {
            FieldEntity<?> f = (FieldEntity<?>) datum;
            IMeta fieldMeta = f.getMeta();
            dynamic.addProperty(fieldMeta.getKey(), f.getData());
        }
        return dynamic;
    }

    private DynamicEntity resolveAsView(ViewEntity view) {

        MetaView meta = view.getMeta();
        List<FormEntity> data = view.getSubFormEntities();

        Optional<String> businessName = findBusinessName(meta, data);
        DynamicEntity dynamic = new DynamicEntity(businessName.orElse(meta.getCaption()), meta.getKey());
        for (IEntity datum : data) {
            if (datum instanceof FieldEntity) {
                FieldEntity<?> f = (FieldEntity<?>) datum;
                IMeta fieldMeta = f.getMeta();
                dynamic.addProperty(fieldMeta.getKey(), f.getData());
            } else {
                throw new RuntimeException("不是fieldEntity类型: " + data.getClass().getName());
            }
        }
        return dynamic;
    }

    /**
     * 查找可以代表form的代表字段的值；比如form是描述一个人的，这里通常会返回这个人的姓名
     *
     * @param meta
     * @param data
     * @return
     */
    private Optional<String> findBusinessName(MetaCollection meta, List<? extends IEntity> data) {
        if (data == null) return Optional.empty();
        Map<String, IEntity> collect = data.stream().collect(Collectors.toMap(e -> e.getMeta().getKey(), e -> e));

        String businessKey = meta.getBusinessKey();
        if (StringUtils.isNotBlank(businessKey)) {
            IEntity iEntity = collect.get(businessKey);
            if (iEntity != null) {
                return Optional.ofNullable((String) iEntity.getData());
            }
        }
        return Optional.empty();
    }
}
