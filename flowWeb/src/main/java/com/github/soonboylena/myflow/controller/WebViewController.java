package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.persistentneo4j.service.DynamicFormService;
import com.github.soonboylena.myflow.service.WebFormService;
import com.github.soonboylena.myflow.service.WebLayoutService;
import com.github.soonboylena.myflow.support.UrlManager;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.uiAction.SubmitAction;
import com.github.soonboylena.myflow.vModel.uiComponent.Button;
import com.github.soonboylena.myflow.vModel.uiComponent.Form;
import com.github.soonboylena.myflow.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.vModel.uiComponent.UrlSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("page")
public class WebViewController {

    @Autowired
    private WebLayoutService webLayoutService;

    /**
     * 领域服务
     */
    @Autowired
    private WebFormService webFromSvs;

    /**
     * 持久层服务
     */
    @Autowired
    private DynamicFormService dynamicFormService;

    @GetMapping("init/v-{viewKey}")
    public UrlSection init(@PathVariable("viewKey") String viewKey) {
        return new UrlSection(UrlManager.viewLayout(viewKey));
    }

    @GetMapping("layout/v-{viewKey}")
    public UiObject layout(@PathVariable("viewKey") String viewKey) {

        Page page = webLayoutService.buildViewLayout(viewKey);

        SubmitAction clientAction = new SubmitAction(UrlManager.submit(viewKey));
        Button button = new Button("提交", clientAction);

        page.addBtn(button);
        return page;
    }

    @GetMapping("data/v-{viewKey}/{id}")
    public UiObject data(@PathVariable("viewKey") String viewKey, @PathVariable("id") String id) {
//        return webLayoutService.buildLayout(formKey);
        return null;
    }
//
//    @PutMapping("data/v-{formKey}")
//    public AbstractAction pageSubmit(@PathVariable String formKey, @RequestBody Map<String, Map<String, Object>> map) {
//
//        if (map != null) {
//
//            if (map.size() != 1) {
//                return MessageAction.error("现在还不支持多个form提交");
//            }
//            List<IEntity> entityList = new ArrayList<>(map.size());
//            for (String s : map.keySet()) {
//                IEntity iEntity = webFromSvs.cleanUp(s, map.get(s));
//                entityList.add(iEntity);
//            }
//
//            Iterable<DynamicEntity> save = formService.save(entityList);
//
//            List<Long> ids = new ArrayList<>();
//            save.forEach(s -> ids.add(s.getId()));
//            Long aLong = ids.get(0);
//
//            LinkAction action = new LinkAction();
//            action.setAlert("提交成功!");
//            action.setUrl(UrlManager.pageInit(formKey, aLong));
//            return action;
//        }
//
//
//        return null;
//    }
}
