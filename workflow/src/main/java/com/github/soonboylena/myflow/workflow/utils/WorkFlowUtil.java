package com.github.soonboylena.myflow.workflow.utils;

public class WorkFlowUtil {

    // 代理引擎的名字
    public static final String EngineName = "dynamicForm_engine";

    // form自定义后缀
    public static final String formKeySuffix = ".mfl";


    // activiti里边设置的formkey有后缀，替换掉
    public static String noSuffixFormKey(String processFormKey) {
        return processFormKey.replace(formKeySuffix, "");
    }


}
