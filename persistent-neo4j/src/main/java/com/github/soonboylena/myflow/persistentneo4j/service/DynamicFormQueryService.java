package com.github.soonboylena.myflow.persistentneo4j.service;

import com.github.soonboylena.myflow.entity.core.*;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicRelation;
import com.github.soonboylena.myflow.persistentneo4j.repository.DynamicFormGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DynamicFormQueryService {

    @Autowired
    private DynamicFormGraphRepository repository;


    /**
     * @param metaForm
     * @param id
     * @return
     */
    public IEntity findById(MetaForm metaForm, Long id) {

        Optional<DynamicEntity> byId = repository.findById(id);

        if (!byId.isPresent()) {
            return null;
        }

        DynamicEntity dynamic = byId.get();
        FormEntity extract = extract(metaForm, dynamic);
        return extract;
    }

    /**
     * 根据metaForm，从Map中找对应的值，并构造出IEntity
     *
     * @param metaForm
     * @param properties
     */

    private FormEntity extract(MetaForm metaForm, DynamicEntity dynamic) {

        if (metaForm == null) return null;

        FormEntity formEntity = new FormEntity(metaForm);
        if (dynamic == null) return formEntity;

        for (Map.Entry<String, MetaField> stringMetaFieldEntry : metaForm.getMetaPool().entrySet()) {
            String key = stringMetaFieldEntry.getKey();
            Object property = dynamic.getProperty(key);
            formEntity.setDatum(key, property);
        }

        Collection<Relation> relations = metaForm.getRelations();
        if (relations == null) return formEntity;

        //处理关联关系
        for (Relation relation : relations) {
            List<MetaForm> relatedForms = relation.getRelatedForm();
            String type = relation.getType();
            List<DynamicRelation> relationShip = dynamic.getRelationShip(type);

            for (int i = 0; i < relatedForms.size(); i++) {
                MetaForm relatedForm = relatedForms.get(i);
                if (relationShip.size() > i) {
                    formEntity.addRelatedForm(type, extract(relatedForm, relationShip.get(i).getEnd()));
                }
            }
        }

        return formEntity;
    }

}
