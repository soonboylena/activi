package com.github.soonboylena.activiti.vModel.uiComponent.domain;

import com.github.soonboylena.activiti.vModel.AbstractDwc;
import com.github.soonboylena.activiti.vModel.IUiDefinition;
import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.activiti.vModel.uiComponent.DetailTable;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * UserEntity:            sunb
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
