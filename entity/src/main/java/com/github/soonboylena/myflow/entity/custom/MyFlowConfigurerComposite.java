package com.github.soonboylena.myflow.entity.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MyFlowConfigurerComposite implements MyFlowWebConfigurer {

    private List<MyFlowWebConfigurer> delegates = new ArrayList<>();

    public void addMyFlowWebConfigures(Collection<MyFlowWebConfigurer> configurerCollections) {
        if (configurerCollections != null) {
            delegates.addAll(configurerCollections);
        }
    }

//    public void registValidator(EntityValidatorConfigure validator) {
//        for (MyFlowWebConfigurer delegate : delegates) {
//            delegate.registValidator(validator);
//        }
//    }


}
