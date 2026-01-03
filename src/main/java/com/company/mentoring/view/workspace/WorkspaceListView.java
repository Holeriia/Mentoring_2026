package com.company.mentoring.view.workspace;

import com.company.mentoring.entity.Workspace;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "workspaces", layout = MainView.class)
@ViewController(id = "Workspace.list")
@ViewDescriptor(path = "workspace-list-view.xml")
@LookupComponent("workspacesDataGrid")
@DialogMode(width = "64em")
public class WorkspaceListView extends StandardListView<Workspace> {
}