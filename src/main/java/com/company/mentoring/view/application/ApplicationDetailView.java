 package com.company.mentoring.view.application;

import com.company.mentoring.app.ApplicationAutoFillService;
import com.company.mentoring.app.ApplicationProcessService;
import com.company.mentoring.entity.*;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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