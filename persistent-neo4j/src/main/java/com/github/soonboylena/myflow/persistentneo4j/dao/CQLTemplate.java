package com.github.soonboylena.myflow.persistentneo4j.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CQLTemplate {

    private final static Logger logger = LoggerFactory.getLogger(CQLTemplate.class);

    private String command = "CREATE";
    private String nodeName = "n";
    private String labelName;
    private Map<String, Object> properties = new LinkedHashMap<>();

    private CQLTemplate(String labelName) {
        this.labelName = labelName;
    }

    public CQLTemplate property(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    public static CQLTemplate create(String labelName) {
        return new CQLTemplate(labelName);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Object[] keysAndValues() {

        if (properties != null && properties.size() > 0) {

            List<Object> list = new ArrayList<>(properties.size() * 2);
            for (Map.Entry<String, Object> stringObjectEntry : properties.entrySet()) {
                list.add(stringObjectEntry.getKey());
                list.add(stringObjectEntry.getValue());
            }

            if (logger.isDebugEnabled()) {
                StringBuilder b = new StringBuilder();
                for (Object o : list) {
                    b.append(o);
                    b.append(",");
                }
                logger.debug(b.toString());
            }

            return list.toArray();
        }
        logger.debug("no properties");

        return null;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s (%s:%s ", command, nodeName, labelName));
        if (properties != null && properties.size() > 0) {
            builder.append("{");
            for (String key : properties.keySet()) {
                builder.append(String.format("%s:$%s,", key, key));
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("}");
        }
        builder.append(")");

        String s = builder.toString();
        logger.debug(s);
        return s;
    }
}
