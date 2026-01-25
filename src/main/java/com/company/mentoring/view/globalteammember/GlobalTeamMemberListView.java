package com.company.mentoring.view.globalteammember;

import com.company.mentoring.entity.GlobalTeamMember;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "global-team-members", layout = MainView.class)
@ViewController(id = "GlobalTeamMember.list")
@ViewDescriptor(path = "global-team-member-list-view.xml")
@LookupComponent("globalTeamMembersDataGrid")
@DialogMode(width = "64em")
public class GlobalTeamMemberListView extends StandardListView<GlobalTeamMember> {
}