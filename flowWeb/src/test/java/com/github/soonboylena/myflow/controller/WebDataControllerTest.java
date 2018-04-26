package com.github.soonboylena.myflow.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.soonboylena.myflow.support.UrlManager;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;

public class WebDataControllerTest extends ControllerTest {

    @Test
    public void list() throws Exception {
        MvcResult mvcResult = mockMvc.perform
                (
                        get(UrlManager.dataList("company2").asUrlString())
                                .contextPath(UrlManager.prefix)
                )
                .andExpect(status().isOk())
                .andReturn();
        print(mvcResult, "一览数据");
    }

    @Test
    public void listResources() throws Exception {
        MvcResult mvcResult = mockMvc.perform
                (
                        get(UrlManager.getResources("customer").asUrlString())
                                .contextPath(UrlManager.prefix)
                )
                .andExpect(status().isOk())
                .andReturn();
        print(mvcResult, "一览下拉选择");
    }

    @Test
    public void data() throws Exception {
        MvcResult mvcResult = mockMvc.perform
                (
                        get(UrlManager.data("company2", 56L).asUrlString())
                                .contextPath(UrlManager.prefix)
                )
                .andExpect(status().isOk())
                .andReturn();
        print(mvcResult, "动态表单数据");
    }
}