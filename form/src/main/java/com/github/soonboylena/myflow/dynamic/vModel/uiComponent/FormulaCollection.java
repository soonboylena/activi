package com.github.soonboylena.myflow.dynamic.vModel.uiComponent;

import com.github.soonboylena.myflow.dynamic.vModel.AbstractDwc;
import com.github.soonboylena.myflow.dynamic.vModel.IUiDefinition;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormulaCollection extends AbstractDwc<FormulaDefinition> {

    private static final String type = "m-formulaCollection";

    public FormulaCollection() {
        setDefine(new FormulaDefinition());
    }

    @Override
    public String getType() {
        return type;
    }

    public void add(String formula, String resfkey, Map<String, String> refFields) {
        getDefine().add(formula, resfkey, refFields);
    }
}

@Data
class FormulaDefinition implements IUiDefinition {

    List<Map<String, Object>> formulas = new ArrayList<>();

    public void add(String formula, String resfkey, Map<String, String> fkeys) {
        Map<String, Object> formulaMap = new HashMap<>();
        formulaMap.put("formula", formula);
        formulaMap.put("resultField", resfkey);
        formulaMap.put("refField", fkeys);
        formulas.add(formulaMap);
    }
}

