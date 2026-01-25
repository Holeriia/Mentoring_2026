package com.company.mentoring.view.team;

import com.company.mentoring.entity.WorkspaceTeam;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "teams/:id", layout = MainView.class)
@ViewController(id = "WorkspaceTeam.detail")
@ViewDescriptor(path = "team-detail-view.xml")
@EditedEntityContainer("teamDc")
public class TeamDetailView extends StandardDetailView<WorkspaceTeam> {
}