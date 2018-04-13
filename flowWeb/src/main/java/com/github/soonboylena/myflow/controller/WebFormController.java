package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.service.WebLayoutService;
import com.github.soonboylena.myflow.support.UrlManager;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.contant.ButtonType;
import com.github.soonboylena.myflow.vModel.uiAction.SubmitAction;
import com.github.soonboylena.myflow.vModel.uiAction.UrlObject;
import com.github.soonboylena.myflow.vModel.uiComponent.Button;
import com.github.soonboylena.myflow.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.vModel.uiComponent.UrlSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("page")
public class WebFormController {

    @Autowired
    private WebLayoutService webLayoutService;


    /**
     * 新建画面
     *
     * @param formKey
     * @return
     */
    @GetMapping("init/{formKey}")
    public UrlSection init(@PathVariable("formKey") String formKey) {
        return new UrlSection(UrlManager.formLayout(formKey));
    }

    /**
     * 更新画面；有数据的情况
     *
     * @param formKey
     * @param id
     * @return
     */
    @GetMapping("init/{formKey}/{id}")
    public UrlSection init(@PathVariable("formKey") String formKey, @PathVariable("id") Long id) {
        UrlObject defineUrl = UrlManager.formLayout(formKey);
        UrlSection urlSection = new UrlSection(defineUrl);
        urlSection.setDataUrl(UrlManager.data(formKey, id));
        return urlSection;
    }

    @GetMapping("layout/{formKey}")
    public UiObject layout(@PathVariable("formKey") String formKey) {

        Page page = new Page();
        webLayoutService.buildFormLayout(formKey, page);
        SubmitAction clientAction = new SubmitAction(UrlManager.submit(formKey));
        Button button = new Button("提交", ButtonType.primary, clientAction);

        page.addBtn(button);
        return page;
    }

}
