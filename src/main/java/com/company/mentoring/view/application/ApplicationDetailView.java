 package com.company.mentoring.view.application;

import com.company.mentoring.app.ApplicationProcessService;
import com.company.mentoring.entity.*;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;



 @Route(value = "applications/:id", layout = MainView.class)
@ViewController(id = "Application.detail")
@ViewDescriptor(path = "application-detail-view.xml")
@EditedEntityContainer("applicationDc")
 public class ApplicationDetailView extends StandardDetailView<Application> {

     @Autowired
     private ApplicationProcessService applicationProcessService;

     @Subscribe("startProcessBtn")
     public void onStartProcessBtnClick(final ClickEvent<JmixButton> event) {
         applicationProcessService.startProcess(getEditedEntity());
     }
 }