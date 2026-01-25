package com.company.mentoring.view.globalteam;

import com.company.mentoring.entity.GlobalTeam;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "global-teams", layout = MainView.class)
@ViewController(id = "GlobalTeam.list")
@ViewDescriptor(path = "global-team-list-view.xml")
@LookupComponent("globalTeamsDataGrid")
@DialogMode(width = "64em")
public class GlobalTeamListView extends StandardListView<GlobalTeam> {
}