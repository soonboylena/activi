package com.github.soonboylena.myflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.soonboylena.myflow.BaseTest;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.UrlObject;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/***
 * 继承这个类进行测试，把MockMvc放进去
 */


public class ControllerTest extends BaseTest {

    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    protected void print(MvcResult result, String title) {

        String contentAsString = null;
        try {
            System.out.println("===================================");
            System.out.println("=" + title);
            System.out.println("===================================");

            contentAsString = result.getResponse().getContentAsString();
            Object json = mapper.readValue(contentAsString, Object.class);
            String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            System.out.println(indented);
        } catch (Exception e) {
            System.out.println("转json的时候挂了。");
            System.out.println(e.getMessage());
            System.out.println("原始:  " + contentAsString);
        }
    }

    byte[] readJsonFromFile(String path) throws IOException, URISyntaxException {
        return Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(path).toURI()));
    }

    String readJsonAndReplace(String path) throws IOException, URISyntaxException {

        byte[] bytes = readJsonFromFile(path);
        String str = new String(bytes, Charset.forName("UTF-8"));
        String mmss = str.replaceAll("\\$T", LocalTime.now().format(DateTimeFormatter.ofPattern("hhmmss")));
        return mmss;
    }

    protected MockHttpServletRequestBuilder get2(String s) {
        return get(s.replaceAll("/api", "").replaceAll("api", "")).cookie(new Cookie("SESSION", "TEST-SESSION"));
    }

    protected MockHttpServletRequestBuilder get2(UrlObject urlObject) {
        return get2(urlObject.asUrlString());
    }
}
