package com.github.soonboylena.activiti.vModel.uiComponent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.soonboylena.activiti.vModel.AbstractDwc;
import com.github.soonboylena.activiti.vModel.IUiDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * UserEntity:            sunb
 * Date:            2018-01-05
 * Time:            15:34
 */
public class BarLineComposeChart extends AbstractDwc<BarLineComposeChartDefine> {
    private transient static final String TYPE = "barLineComposeChart";

    public BarLineComposeChart () {
        setDefine(new BarLineComposeChartDefine());
        getDefine().setLegend(new Legend());
        getDefine().setSeries(new ArrayList<>());
        getDefine().setTitle(new Title());
        getDefine().setXAxis(new ArrayList<>());
        getDefine().setYAxis(new ArrayList<>());
        getDefine().setTooltip(new ArrayList<>());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void addLegend (String data) {
        getDefine().getLegend().getData().add(data);
    }

    public void setTitle (String text, String subtext) {
        getDefine().getTitle().setText(text);
        getDefine().getTitle().setSubtext(subtext);
    }

    public void addXAxis (List<String> data, String type,String unit) {
        Axis axis = new Axis(AxisType.of(type), data, unit);

        getDefine().getXAxis().add(axis);
    }

    public void addYAxis (List<String> data, String type,String unit) {
        Axis axis = new Axis(AxisType.of(type), data, unit);

        getDefine().getYAxis().add(axis);
    }

    public void addSerie (String type, List<String> data, String name, int yAxisIndex, int xAxisIndex) {
        Serie serie = new Serie(SerieType.of(type), data, name, yAxisIndex, xAxisIndex);
        getDefine().getSeries().add(serie);
    }

    public void addTooltip (String label, String value) {
        getDefine().getTooltip().add(new Tooltip(label, value));
    }

 }

@Data
@NoArgsConstructor
class BarLineComposeChartDefine implements IUiDefinition {
    private Legend legend;
    @JsonProperty("xAxis")
    private List<Axis> xAxis;
    @JsonProperty("yAxis")
    private List<Axis> yAxis;
    private List<Serie> series;
    private Title title;
    private List<Tooltip> tooltip;
}

@Data
class Legend {
    private List<String> data;

    public Legend () {
        setData(new ArrayList<>());
    }
}

@Data
@AllArgsConstructor
class Axis {
    private AxisType type;
    private List<String> data;
    private String unit;
}

@Data
@AllArgsConstructor
class Serie {
    private SerieType type;
    private List<String> data;
    private String name;
    @JsonProperty("yAxisIndex")
    private int yAxisIndex;
    @JsonProperty("xAxisIndex")
    private int xAxisIndex;
}

@Data
class Title {
    private String text;
    private String subtext;
}

@Data
@AllArgsConstructor
class Tooltip {
    private String label;
    private String value;
}

enum AxisType {
    value, category;

    public static AxisType of(String axisType) {
        for (AxisType type : AxisType.values()) {
            if (type.name().equals(axisType)) {
                return type;
            }
        }
        throw new IllegalStateException("错误的AxisType类型表识:" + axisType);
    }
}

enum SerieType {
    bar, line;

    public static SerieType of(String serieType) {
        for (SerieType type : SerieType.values()) {
            if (type.name().equals(serieType)) {
                return type;
            }
        }
        throw new IllegalStateException("错误的SerieType类型表识:" + serieType);
    }
}