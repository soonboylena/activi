package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.BaseTest;
import com.github.soonboylena.myflow.vModel.UiContainer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WebLayoutServiceTest extends BaseTest {

    @Autowired
    private WebLayoutService service;

    @Test
    public void buildFormLayout() {
    }

    @Test
    public void listLayout() {
        UiContainer company2 = service.listLayout("company2");
        print(company2, "一览画面的定义");
    }
}