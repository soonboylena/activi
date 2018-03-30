package com.github.soonboylena.myflow.vModel;

import com.github.soonboylena.myflow.vModel.uiComponent.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-07
 * Time:            13:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedList {

    private transient final static PagedList static_empty = new PagedList(Collections.EMPTY_LIST);

    private Collection<RowData> data = new ArrayList<>(30);
    private List<String> wordList = new ArrayList<>();

    public PagedList(Collection<RowData> data) {
        this.data = data;
    }

    public static PagedList empty() {
        return static_empty;
    }

    public void add(RowData rowData) {
        this.data.add(rowData);
    }


    public static class RowData extends HashMap {
        List<Button> buttons = new ArrayList<>(5);
        private String id;

        public List<Button> addButton(Button button) {
            buttons.add(button);
            return buttons;
        }

        public List<Button> getButtons() {
            return buttons;
        }

        public void addButtons(List<Button> buttons) {
            this.buttons.addAll(buttons);
            put("buttons", buttons);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
            put("id", id);
        }
    }

    public Integer getTotal() {
//        return data == null ? 0 : data.size();
        return null;
    }
}

