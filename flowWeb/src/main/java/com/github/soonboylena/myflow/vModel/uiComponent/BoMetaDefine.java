package com.github.soonboylena.myflow.vModel.uiComponent;

import com.github.soonboylena.myflow.vModel.IUiDefinition;

import java.util.ArrayList;
import java.util.List;

public class BoMetaDefine implements IUiDefinition {

    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
    }

    //==============================================
    public static class Link {

        private Template template;
        private List<Meta> children;

        public Link() {
        }

        public Link(Template template, List<Meta> children) {
            this.template = template;
            this.children = children;
        }

        public Template getTemplate() {
            return template;
        }

        public List<Meta> getChildren() {
            return children;
        }

        public void setTemplate(Template template) {
            this.template = template;
        }

        public void setChildren(List<Meta> children) {
            this.children = children;
        }

        public void addChild(Meta meta) {
            if (this.children == null) {
                this.children = new ArrayList<>();
            }
            children.add(meta);
        }
    }

    public static class Meta {
        private String metaId;
        private List<Button> buttons;

        public Meta(String metaId, List<Button> buttons) {
            this.metaId = metaId;
            this.buttons = buttons;
        }

        public Meta() {
        }

        public String getMetaId() {
            return metaId;
        }

        public List<Button> getButtons() {
            return buttons;
        }

        public void setMetaId(String metaId) {
            this.metaId = metaId;
        }

        public void setButtons(List<Button> buttons) {
            this.buttons = buttons;
        }

        public void addButton(Button button) {
            if (buttons == null) {
                buttons = new ArrayList<>();
            }
            this.buttons.add(button);
        }
    }

    public static class Button {
        private String url;
        private String type;
        private String text;

        public Button(String url, String type, String text) {
            this.url = url;
            this.type = type;
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public String getType() {
            return type;
        }

        public String getText() {
            return text;
        }
    }

    public static class Template {
        private String url;
        private String title;
        private String type = "primary";

        public Template(String url, String name) {
            this.url = url;
            this.title = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}



