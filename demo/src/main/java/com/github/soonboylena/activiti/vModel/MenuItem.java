package com.github.soonboylena.activiti.vModel;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单的一个项目的结构
 */
@Data
public class MenuItem implements Serializable {

    private static final long serialVersionUID = 3472107472737551256L;

    private String code;
    private String title;
    private String icon;
    private String url;
    private String detail;
    private String picUrl;

//    public String getUrl() {
//        try {
//            return URLEncoder.encode(url, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            //
//        }
//        return "";
//    }
}
