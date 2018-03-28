package com.github.soonboylena.activiti.vModel.uiAction;

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
        this(url, pathParams, HttpMethod.GET);
    }

    public UrlObject(String urlString) {
        this.url = urlString;
        this.method = HttpMethod.GET;
    }

    public UrlObject(String url, Map<String, Object> pathParams, HttpMethod urlMethod) {
        this(url, urlMethod, pathParams, null);
    }

    public UrlObject(String url, HttpMethod urlMethod, Map<String, Object> pathParams, Map<String, Object> body) {
        this(url, urlMethod, pathParams, null, body);
    }

    public UrlObject(String url, Map<String, Object> pathParams, Map<String, Object> queryParamMap) {
        this(url, HttpMethod.GET, pathParams, queryParamMap, null);
    }

    public UrlObject(String url, String method, Map<String, Object> pathParams) {
        this(url, HttpMethod.GET, pathParams, null);

    }

    public UrlObject putQueryPair(String queryKey, String queryValue) {
        putQueryPair(queryKey, queryValue, true);
        return this;
    }

    public UrlObject putQueryPair(String queryKey, String queryValue, boolean required) {
        Objects.requireNonNull(queryKey);
        if (required) {
            Objects.requireNonNull(queryValue, "UrlObject的查询参数的值不能为空或null。key：" + queryKey);
        }
        if (queryParams == null) {
            queryParams = new HashMap<>(5);
        }

        if (StringUtils.isNotBlank(queryValue)) {
            queryParams.put(queryKey, queryValue);
        }
        return this;
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
