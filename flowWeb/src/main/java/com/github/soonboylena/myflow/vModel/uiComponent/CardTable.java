package com.github.soonboylena.myflow.vModel.uiComponent;

import com.github.soonboylena.myflow.vModel.AbstractDwc;
import com.github.soonboylena.myflow.vModel.IUiDefinition;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-13
 * Time:            14:11
 */
public class CardTable extends AbstractDwc<CardTableDefinition> {

    private static final transient String TYPE = "mCardTable";

    @Override
    public String getType() {
        return TYPE;
    }

    public CardTable() {
        setDefine(new CardTableDefinition());
    }

    public void addData(List<Map<String, String>> oneData) {
        getDefine().add(oneData);
    }
}

@Data
class CardTableDefinition implements IUiDefinition {
    private List<List<Map<String, String>>> datas = new ArrayList<>();

    public void add(List<Map<String, String>> oneData) {
        datas.add(oneData);
    }
}
