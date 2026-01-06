package com.company.mentoring.view.workspacedashboard;


import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.WorkspaceParticipant;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;

import java.util.List;
import java.util.UUID;

@Route(value = "workspace-dashboard-view", layout = MainView.class)
@ViewController(id = "WorkspaceDashboardView")
@ViewDescriptor(path = "workspace-dashboard-view.xml")
public class WorkspaceDashboardView extends StandardView {

    @ViewComponent
    private CollectionLoader<Application> applicationsDl;

    @ViewComponent
    private CollectionLoader<WorkspaceParticipant> participantsDl;

    @Subscribe
    public void onQueryParametersChange(QueryParametersChangeEvent event) {
        List<String> workspaceIds = event.getQueryParameters().getParameters().get("workspaceId");

        if (workspaceIds != null && !workspaceIds.isEmpty()) {
            UUID workspaceId = UUID.fromString(workspaceIds.get(0));

            participantsDl.setParameter("workspaceId", workspaceId);
            applicationsDl.setParameter("workspaceId", workspaceId);

            participantsDl.load();
            applicationsDl.load();
        }
    }

    // опционально: если кто‑то будет открывать этот экран программно не через URL
    public void setWorkspaceId(UUID workspaceId) {
        applicationsDl.setParameter("workspaceId", workspaceId);
        applicationsDl.load();
    }

    @ViewComponent("applicationsDataGrid.create")
    private CreateAction<Application> applicationCreateAction;

    @Subscribe
    public void onInit(InitEvent event) {
        applicationCreateAction.setOpenMode(OpenMode.DIALOG);
    }

}