package com.github.soonboylena.myflow.vModel;/** * Created with IntelliJ IDEA. * Description: * Project Name:    spring-boot-admin * LoginInfoEntity:            sunb * Date:            2017-10-28 * Time:            11:33 */public interface UiObject<D extends IUiDefinition> {    public D getDefine();    public String getType();}