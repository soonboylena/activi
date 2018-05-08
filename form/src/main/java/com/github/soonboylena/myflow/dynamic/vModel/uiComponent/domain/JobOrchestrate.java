package com.github.soonboylena.myflow.dynamic.vModel.uiComponent.domain;

import com.github.soonboylena.myflow.dynamic.vModel.AbstractDwc;
import com.github.soonboylena.myflow.dynamic.vModel.IUiDefinition;
import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.DetailTable;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-11
 * Time:            16:08
 */
public class JobOrchestrate extends AbstractDwc<JobOrchestrateDefine> {

    private static final transient String TYPE = "mJobOrchestrate";

    @Override
    public String getType() {
        return TYPE;
    }

    public JobOrchestrate() {
        setDefine(new JobOrchestrateDefine());
    }

    public void setPushDownTableDefine(DetailTable tableDefine) {
        getDefine().setTable(tableDefine);
    }

    public void editedJob(UiObject uiObject) {
        getDefine().setTaskTrack(uiObject);
    }
}

@Data
class JobOrchestrateDefine implements IUiDefinition {
    private DetailTable table;
    private UiObject taskTrack;
}
