package com.company.mentoring.view.globalteam;

import com.company.mentoring.entity.GlobalTeam;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "global-teams/:id", layout = MainView.class)
@ViewController(id = "GlobalTeam.detail")
@ViewDescriptor(path = "global-team-detail-view.xml")
@EditedEntityContainer("globalTeamDc")
public class GlobalTeamDetailView extends StandardDetailView<GlobalTeam> {
}