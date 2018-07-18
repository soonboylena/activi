package com.github.soonboylena.myflow.persistentneo4j.service;

import com.github.soonboylena.myflow.entity.core.*;
import com.github.soonboylena.myflow.framework.web.FormSaveService;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.DynamicFormGraphRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DynamicFormFormSaveService implements FormSaveService {

    @Autowired
    private DynamicFormGraphRepository repository;

    /**
     * 保存
     *
     * @param iEntity
     * @return
     */
    @Override
    public Long save(IEntity iEntity) {
        DynamicEntity entity = resolve(iEntity);
        DynamicEntity save = repository.save(entity);
        return save.getId();
    }

    /**
     * 根据类型分类，走不同的插入流程
     *
     * @param iEntity
     * @return
     */
    private DynamicEntity resolve(IEntity iEntity) {

        if (iEntity instanceof FormEntity) {

            FormEntity form = (FormEntity) iEntity;
            return resolveAsForm(form);
        }

        throw new RuntimeException("不是Entity类型: " + iEntity.getClass().getName());

    }

    private DynamicEntity resolveAsForm(FormEntity form) {

        MetaForm meta = form.acquireMeta();
        List<FieldEntity> data = form.getFieldEntities();
        Optional<String> businessName = findBusinessName(meta, data);
        DynamicEntity dynamic = new DynamicEntity(businessName.orElse(meta.getCaption()), meta.getKey());
        for (IEntity datum : data) {
            FieldEntity<?> f = (FieldEntity<?>) datum;
            IMeta fieldMeta = f.acquireMeta();
            dynamic.addProperty(fieldMeta.getKey(), f.getData());
            dynamic.setId(form.getId());
        }
        for (Relation relation : meta.getRelations()) {
            String type = relation.getType();
            List<FormEntity> relatedEntities = form.getRelations(type);
            for (FormEntity relatedEntity : relatedEntities) {
                DynamicEntity entity = resolveAsForm(relatedEntity);
                dynamic.addRelation(type, entity);
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
        Map<String, IEntity> collect = data.stream().collect(Collectors.toMap(e -> e.acquireMeta().getKey(), e -> e));

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
