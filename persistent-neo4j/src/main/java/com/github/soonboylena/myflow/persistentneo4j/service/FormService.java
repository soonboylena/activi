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

    public Iterable<DynamicEntity> save(List<IEntity> entityList) {

        List<DynamicEntity> dynamicEntities = new ArrayList<>();
        for (IEntity iEntity : entityList) {
            DynamicEntity entity = resolve(iEntity);
            dynamicEntities.add(entity);
        }
        return repository.saveAll(dynamicEntities);
    }

    private DynamicEntity resolve(IEntity iEntity) {


        if (iEntity instanceof FormEntity) {
            FormEntity _entity = (FormEntity) iEntity;

            MetaForm meta = _entity.getMeta();
            List<IEntity> data = _entity.getData();

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

        throw new RuntimeException("不是FormEntity类型: " + iEntity.getClass().getName());

    }

    /**
     * 查找可以代表form的代表字段的值；比如form是描述一个人的，这里通常会返回这个人的姓名
     *
     * @param meta
     * @param data
     * @return
     */
    private Optional<String> findBusinessName(MetaForm meta, List<IEntity> data) {
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
