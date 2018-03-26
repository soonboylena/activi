package com.github.soonboylena.activiti.component.layout;

import com.github.soonboylena.activiti.component.layout.converter.ItemConverter;
import com.github.soonboylena.activiti.component.layout.converter.StringItemConverter;
import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.entity.core.MetaItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemConverters {

    private List<ItemConverter> converterList = new ArrayList<>();

    public ItemConverters() {
        converterList.add(new StringItemConverter());
    }

    public UiObject convert(MetaItem metaItem) {

        for (ItemConverter itemConverter : converterList) {
            if (itemConverter.support(metaItem)) {
                return itemConverter.convert(metaItem);
            }
        }
        return null;
    }
}
