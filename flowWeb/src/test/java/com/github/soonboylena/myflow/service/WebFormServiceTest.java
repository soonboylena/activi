package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.BaseTest;
import com.github.soonboylena.myflow.dynamic.service.WebFormService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class WebFormServiceTest extends BaseTest {


    @Autowired
    private WebFormService webFormService;

    @Test
    public void loadData() {
        Map<String, Map<String, Object>> company2 = webFormService.findById("company2", 13L);
        Assert.assertEquals(3, company2.size());
        print(company2, "取回来的数据");
    }
}