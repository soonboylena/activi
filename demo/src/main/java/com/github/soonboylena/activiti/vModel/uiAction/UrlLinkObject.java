package com.github.soonboylena.activiti.vModel.uiAction;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZhaoPeng
 * @Date: 2017/11/23 11:39
 * @Description: 
 */
@Data
@NoArgsConstructor
public class UrlLinkObject extends UrlObject{

    public UrlLinkObject(String urlString) {
        this(urlString, null);
    }

    public UrlLinkObject (String url, Map<String, Object> pathParams) {
        this(url, pathParams, "GET");
    }

    public UrlLinkObject(String url, Map<String, Object> pathParams, String urlMethod) {
        this(url, urlMethod, pathParams, null);
    }

    public UrlLinkObject(String url, String urlMethod, Map<String, Object> pathParams, Map<String, Object> body) {
        this.url = url;
        this.method = urlMethod;
        this.pathParams = pathParams;
        this.body = body;
    }

    public void setBodyRelation (String key, ParamsRelation relation) {
        if(null != body) {
            body.put(key, relation);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(key, relation);
            body = map;
        }
    }

    public void setPathRelation (String key, ParamsRelation relation) {
        if(null != pathParams) {
            pathParams.put(key, relation);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(key, relation);
            pathParams = map;
        }
    }

    public void setQueryRelation (String key, ParamsRelation relation) {
        if(null != queryParams) {
            queryParams.put(key, relation);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(key, relation);
            queryParams = map;
        }
    }

}
