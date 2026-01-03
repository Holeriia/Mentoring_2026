package com.company.mentoring.view.workspaceparticipant;

import com.company.mentoring.entity.WorkspaceParticipant;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "workspace-participants", layout = MainView.class)
@ViewController(id = "WorkspaceParticipant.list")
@ViewDescriptor(path = "workspace-participant-list-view.xml")
@LookupComponent("workspaceParticipantsDataGrid")
@DialogMode(width = "64em")
public class WorkspaceParticipantListView extends StandardListView<WorkspaceParticipant> {
}