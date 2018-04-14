package com.github.soonboylena.myflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {
    protected ObjectMapper mapper = new ObjectMapper();

    protected void print(Object build, String title) {

        try {
            System.out.println("===================================");
            System.out.println("=" + title);
            System.out.println("===================================");

            String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(build);
            System.out.println(indented);
        } catch (Exception e) {
            System.out.println("转json的时候挂了。");
            System.out.println(e.getMessage());
            System.out.println("原始:  " + build);
        }
    }
}
