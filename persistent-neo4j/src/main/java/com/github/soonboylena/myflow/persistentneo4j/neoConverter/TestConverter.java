package com.github.soonboylena.myflow.persistentneo4j.neoConverter;

import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.Collections;
import java.util.Map;

public class TestConverter implements AttributeConverter<Map, String> {

    @Override
    public String toGraphProperty(Map value) {
        System.out.println(value);
        return "xxxx";
    }

    @Override
    public Map toEntityAttribute(String value) {
        System.out.println(value);
        return Collections.singletonMap("aaa", "bbb");
    }
}
