package com.company.mentoring.view.globalteammember;

import com.company.mentoring.entity.GlobalTeamMember;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "global-team-members/:id", layout = MainView.class)
@ViewController(id = "GlobalTeamMember.detail")
@ViewDescriptor(path = "global-team-member-detail-view.xml")
@EditedEntityContainer("globalTeamMemberDc")
public class GlobalTeamMemberDetailView extends StandardDetailView<GlobalTeamMember> {
}