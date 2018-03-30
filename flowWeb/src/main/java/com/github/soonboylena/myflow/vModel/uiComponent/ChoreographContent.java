package com.github.soonboylena.myflow.vModel.uiComponent;

import com.github.soonboylena.myflow.vModel.AbstractDwc;
import com.github.soonboylena.myflow.vModel.IUiDefinition;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.uiAction.LinkAction;
import com.github.soonboylena.myflow.vModel.uiAction.UrlObject;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-13
 * Time:            15:06
 */

public class ChoreographContent extends AbstractDwc<ChoreographContentDefine> {

    private transient final static String TYPE = "mChoreographContent";

    @Override
    public String getType() {
        return TYPE;
    }

    public ChoreographContent() {
        setDefine(new ChoreographContentDefine());
    }

    public void setPushDownTableDefine(DetailTable tableDefine) {
        getDefine().setTable(tableDefine);
    }

    public void editedJob(UiObject uiObject) {
        getDefine().setTaskTrack(uiObject);
    }

    public void setTableName(String tableName) {
        getDefine().setTableName(tableName);
    }

    public void setTableData(List<Map<String, Map<String, String>>> tableData) {
        getDefine().setTableData(tableData);
    }

    public void setJobEditUrl(LinkAction jobEditUrl) {
        getDefine().setAction(jobEditUrl);
    }

    public void setTableStatus(String editable) {
        getDefine().setTableStatus(editable);
    }

    public void setTableColStatus(Map<String, String> statusMap) {
        getDefine().setStatusMap(statusMap);
    }

    public void setSubmitAction(UrlObject urlObject) {
        getDefine().setSubmitUrl(urlObject);
    }
}

@Data
class ChoreographContentDefine implements IUiDefinition {
    // 画面上的table
    private DetailTable table;
    // 任务跟踪部分
    private UiObject taskTrack;
    // table的key值。用metaId设定
    private String tableName;
    // table的数据
    private List<Map<String, Map<String, String>>> tableData;
    // 任务编排按钮按下
    private LinkAction action;
    // table是否可以编辑
    private String tableStatus;
    // table里边列是否可以编辑
    private Map<String, String> statusMap;

    // 表格数据提交的地址
    private UrlObject submitUrl;
}
