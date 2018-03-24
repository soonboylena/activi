package com.github.soonboylena.entity.config;

import com.github.soonboylena.entity.core.MetaForm;
import com.github.soonboylena.entity.core.MetaItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemoryConfigHolder {

    private List<MetaItem> metaItems = new ArrayList<MetaItem>(100);
    private List<MetaForm> metaForms = new ArrayList<MetaForm>();

}
