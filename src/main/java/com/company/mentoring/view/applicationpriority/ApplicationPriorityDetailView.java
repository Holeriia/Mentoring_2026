package com.company.mentoring.view.applicationpriority;

import com.company.mentoring.entity.ApplicationPriority;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "application-priorities/:id", layout = MainView.class)
@ViewController(id = "ApplicationPriority.detail")
@ViewDescriptor(path = "application-priority-detail-view.xml")
@EditedEntityContainer("applicationPriorityDc")
public class ApplicationPriorityDetailView extends StandardDetailView<ApplicationPriority> {
}