package com.github.soonboylena.myflow.workflow.utils;

import com.github.soonboylena.myflow.entity.core.IEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkFlowUtil {

    // 代理引擎的名字
    public static final String EngineName = "dynamicForm_engine";

    // form自定义后缀
    public static final String formKeySuffix = ".mfl";


    // activiti里边设置的formkey有后缀，替换掉
    public static String noSuffixFormKey(String processFormKey) {
        return processFormKey.replace(formKeySuffix, "");
    }


    // ===================================TODO 处理formkey的。最好放到一个类里边
    public static Map<String, String> formKeyMap(IEntity entity, Long id) {
        Map<String, String> formKeyMap = Collections.singletonMap("[entity." + entity.acquireMeta().getKey() + ".id]", String.valueOf(id));
        return formKeyMap;
    }

    public static Map<String, Long> findFormKeyByPattern(Map<String, Object> bindings) {

        String pattern = "\\[entity.(\\S+).id]";
        Pattern compile = Pattern.compile(pattern);

        Map<String, Long> map = new HashMap<>();


        for (Map.Entry<String, Object> stringObjectEntry : bindings.entrySet()) {
            String key = stringObjectEntry.getKey();
            Object value = stringObjectEntry.getValue();

            Matcher matcher = compile.matcher(key);
            if (matcher.matches()) {
                String formKey = matcher.group(1);
                map.put(formKey, Long.parseLong(value.toString()));
            }
        }
        return map;
    }

    // ===================================TODO 处理formkey的。最好放到一个类里边
}
