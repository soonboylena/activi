package com.github.soonboylena.myflow.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WebLayoutControllerTest extends ControllerTest {

    @Test
    public void init() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/page/init/{formKey}", "form1"))
                .andExpect(status().isOk())
                .andReturn();
        print(mvcResult, "init");
    }

    @Test
    public void layout() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/page/layout/{formKey}", "form1"))
                .andExpect(status().isOk())
                .andReturn();
        print(mvcResult, "layout");

    }

    @Test
    public void createData() throws Exception {
        MvcResult mvcResult = mockMvc.perform
                (
                        put("/page/data/{formKey}", "form1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(readJsonAndReplace("form1.json"))
                )
                .andExpect(status().isOk())
                .andReturn();
        print(mvcResult, "data");
    }

}