package com.company.mentoring.view.application;

import com.company.mentoring.entity.*;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "applications/:id", layout = MainView.class)
@ViewController(id = "Application.detail")
@ViewDescriptor(path = "application-detail-view.xml")
@EditedEntityContainer("applicationDc")
public class ApplicationDetailView extends StandardDetailView<Application> {
}