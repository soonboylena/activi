package com.github.soonboylena.myflow.dynamic.vModel.uiComponent;import com.github.soonboylena.myflow.dynamic.vModel.AbstractDwc;import com.github.soonboylena.myflow.dynamic.vModel.IUiDefinition;import lombok.Data;import lombok.EqualsAndHashCode;import java.util.HashMap;import java.util.Map;/** * 这个是过渡期的实现方式； 用一个map来放组件的定义信息； * 现在主要用在各种可输入组件。比如 简单的文字输入，数字输入等； * 一旦这个类超过了50行，就需要分别定义成专用的UiObject的实例，并废弃掉这个类 * Created with IntelliJ IDEA. * Description: * Project Name:    spring-boot-admin * LoginInfoEntity:            sunb * Date:            2017-11-11 * Time:            16:41 */@EqualsAndHashCode(callSuper = true)@Datapublic class MapUiObject<K, V> extends AbstractDwc<MapDefine> {    private String type;    public MapUiObject(String type) {        this.type = type;        setDefine(new MapDefine<K, V>());    }    @Override    public String getType() {        return type;    }    public void put(K key, V object) {        getDefine().put(key, object);    }    public void putAll(Map<String, Object> attrsAsMap) {        getDefine().putAll(attrsAsMap);    }}class MapDefine<K, V> extends HashMap implements IUiDefinition {}