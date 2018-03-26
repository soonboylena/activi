package com.github.soonboylena.entity.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 读取xml文件 保存到数据库
 */
public class XmlConfigureReader {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ResourceLoader defaultResourceLoader = new DefaultResourceLoader();

    public Document read(String location) throws IOException, DocumentException {

        Resource resource = defaultResourceLoader.getResource(location);

        if (!resource.exists()) {
            logger.error("文件不存在。 location: {}", location);
            throw new RuntimeException("文件不存在。");
        }

        if (!resource.isReadable()) {
            logger.error("文件不可读。 location: {}", location);
            throw new RuntimeException("文件不可读。");
        }

        Document document;
        document = readDocument(resource.getInputStream());
        return document;

    }

    private Document readDocument(InputStream inputStream) throws DocumentException {

        SAXReader reader = new SAXReader();
        return reader.read(inputStream);
    }


}
