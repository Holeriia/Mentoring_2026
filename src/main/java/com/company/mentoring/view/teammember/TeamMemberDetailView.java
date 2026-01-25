package com.company.mentoring.view.teammember;

import com.company.mentoring.entity.WorkspaceTeamMember;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "team-members/:id", layout = MainView.class)
@ViewController(id = "WorkspaceTeamMember.detail")
@ViewDescriptor(path = "team-member-detail-view.xml")
@EditedEntityContainer("teamMemberDc")
public class TeamMemberDetailView extends StandardDetailView<WorkspaceTeamMember> {
}