package com.github.soonboylena.activiti.vModel.contant;import com.fasterxml.jackson.annotation.JsonValue;/** * Created with IntelliJ IDEA. * Description: * Project Name:    spring-boot-admin * UserEntity:            sunb * Date:            2017-11-22 * Time:            13:33 */public enum ClientRouterMode {    reload, push, replace;    @JsonValue    public String getText() {        return this.name();    }}