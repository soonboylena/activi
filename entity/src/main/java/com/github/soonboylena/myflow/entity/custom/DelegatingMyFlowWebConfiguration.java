//package com.github.soonboylena.myflow.entity.custom;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
//@Configuration
//public class DelegatingMyFlowWebConfiguration {
//
//    private final MyFlowConfigurerComposite configurers = new MyFlowConfigurerComposite();
//
//    @Autowired(required = false)
//    public void setConfigurers(List<MyFlowWebConfigurer> configurers) {
//        if (!CollectionUtils.isEmpty(configurers)) {
//            this.configurers.addMyFlowWebConfigures(configurers);
//        }
//    }
//
//}
