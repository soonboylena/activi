package com.github.soonboylena.myflow.vModel.uiAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author irvin
 * @date Create in 下午8:02 2017/11/2
 * @description 组件触发事件 url
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlObject {

    private static Logger logger = LoggerFactory.getLogger(UrlObject.class);

    protected String url;
    protected HttpMethod method;
    protected Map<String, Object> pathParams;
    protected Map<String, Object> queryParams;
    protected Map<String, Object> body;

    public UrlObject(String url, Map<String, Object> pathParams) {
        this(url, HttpMethod.GET, pathParams, null, null);
    }

    public UrlObject(String urlString) {
        this.url = urlString;
        this.method = HttpMethod.GET;
    }


    public UrlObject(String url, HttpMethod method) {
        this(url, method, null, null, null);
    }

    public UrlObject(String url, HttpMethod method, Map<String, Object> pathParams) {
        this(url, method, pathParams, null, null);
    }


    public String asUrlString() {

        if (method != HttpMethod.GET) {
            logger.warn("正在将请求转成一个url。这将会做为get处理");
        }

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

        if (StringUtils.isNotBlank(url)) {
            builder = builder.path(StringUtils.prependIfMissing(url, "/"));
        }

        if (queryParams != null) {
            for (Map.Entry<String, ?> stringObjectEntry : queryParams.entrySet()) {
                builder = builder.queryParam(stringObjectEntry.getKey(), stringObjectEntry.getValue());
            }
        }

        URI uri;
        if (pathParams != null) {
            uri = builder.buildAndExpand(pathParams).toUri();
        } else {
            uri = builder.build(true).toUri();
        }

        String s = uri.toASCIIString();
        logger.trace(s);
        return s;
    }
}
