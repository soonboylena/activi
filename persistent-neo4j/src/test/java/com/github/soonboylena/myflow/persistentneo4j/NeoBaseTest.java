package com.github.soonboylena.myflow.persistentneo4j;

import com.alibaba.fastjson.JSON;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NeoBaseTest {

    protected void print(Object result, String title) {

        try {
            System.out.println("===================================");
            System.out.println("=" + title);
            System.out.println("===================================");

            Object o = JSON.toJSON(result);
            System.out.println(o);
        } catch (Exception e) {
            System.out.println("转json的时候挂了。");
            System.out.println(e.getMessage());
            System.out.println("原始:  " + result.toString());
        }
    }
}
