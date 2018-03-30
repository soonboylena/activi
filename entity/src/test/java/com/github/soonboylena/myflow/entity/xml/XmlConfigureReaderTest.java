package com.github.soonboylena.myflow.entity.xml;

import com.github.soonboylena.myflow.entity.support.XmlConfigureReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.IOException;

public class XmlConfigureReaderTest {

    @Test
    public void read() {
        XmlConfigureReader reader = new XmlConfigureReader();
        try {
            Document read = reader.read("classpath:entity.xml");
            String name = read.getRootElement().getName();
            System.out.println(name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}