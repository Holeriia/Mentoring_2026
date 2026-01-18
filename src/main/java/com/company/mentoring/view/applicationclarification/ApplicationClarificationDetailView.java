package com.company.mentoring.view.applicationclarification;

import com.company.mentoring.entity.ApplicationClarification;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "application-clarifications/:id", layout = MainView.class)
@ViewController(id = "ApplicationClarification.detail")
@ViewDescriptor(path = "application-clarification-detail-view.xml")
@EditedEntityContainer("applicationClarificationDc")
public class ApplicationClarificationDetailView extends StandardDetailView<ApplicationClarification> {
}