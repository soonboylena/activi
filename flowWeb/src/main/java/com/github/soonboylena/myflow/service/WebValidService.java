package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import org.springframework.stereotype.Service;

@Service
public class WebValidService {

    public void valid(IEntity entity) {
        IMeta iMeta = entity.acquireMeta();
    }
}
