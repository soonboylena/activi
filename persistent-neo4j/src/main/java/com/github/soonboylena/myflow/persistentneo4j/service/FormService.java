package com.github.soonboylena.myflow.persistentneo4j.service;

import com.github.soonboylena.myflow.entity.core.FieldEntity;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.persistentneo4j.dao.CQLTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormService {

//    @Autowired
//    private TransactDao dao;

    public void save(List<IEntity> entityList) {


        List<CQLTemplate> templates = new ArrayList<>();
        for (IEntity iEntity : entityList) {

            IMeta entityMeta = iEntity.getMeta();
            Object data = iEntity.getData();

            CQLTemplate template = CQLTemplate.create(entityMeta.getKey());

            if (data instanceof List<?>) {
                List entities = (List) data;
                for (Object entity : entities) {
                    if (entity instanceof FieldEntity) {
                        FieldEntity<?> fe = (FieldEntity<?>) entity;
                        template.property(fe.getMeta().getKey(), fe.getData());
                    } else {
                        throw new RuntimeException("类型不对");
                    }
                }
            } else {
                throw new RuntimeException("类型不对2");
            }

            templates.add(template);
        }

        if (!templates.isEmpty()) {
//            dao.execute(templates);
        }
    }
}
