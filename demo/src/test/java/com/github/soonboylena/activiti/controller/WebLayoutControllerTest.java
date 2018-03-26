package com.github.soonboylena.activiti.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WebLayoutControllerTest extends ControllerTest {

    @Test
    public void layout() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/layout/{formKey}", "form1"))
                .andExpect(status().isOk())
                .andReturn();
        print(mvcResult, "layout");

    }
}