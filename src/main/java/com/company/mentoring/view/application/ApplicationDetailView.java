package com.company.mentoring.view.application;

import com.company.mentoring.entity.*;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "applications/:id", layout = MainView.class)
@ViewController(id = "Application.detail")
@ViewDescriptor(path = "application-detail-view.xml")
@EditedEntityContainer("applicationDc")
public class ApplicationDetailView extends StandardDetailView<Application> {

    @Autowired
    private RuntimeService runtimeService;


    @Subscribe(id="startProcessBtn")
    public void onStartProcessBtnClick(final ClickEvent<JmixButton> event) {
        Application application = getEditedEntity();
        System.out.println("Application id = " + application.getId());

        // 2. businessKey = id заявки
        String businessKey = application.getId().toString();

        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(
                        "application-approval",
                        businessKey
                );

        application.setProcessInstanceId(processInstance.getId());
        //application.setStatus("IN_REVIEW");
    }
}