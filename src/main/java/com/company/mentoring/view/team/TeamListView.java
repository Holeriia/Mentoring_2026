package com.company.mentoring.view.team;

import com.company.mentoring.entity.WorkspaceTeam;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "teams", layout = MainView.class)
@ViewController(id = "WorkspaceTeam.list")
@ViewDescriptor(path = "team-list-view.xml")
@LookupComponent("teamsDataGrid")
@DialogMode(width = "64em")
public class TeamListView extends StandardListView<WorkspaceTeam> {
}