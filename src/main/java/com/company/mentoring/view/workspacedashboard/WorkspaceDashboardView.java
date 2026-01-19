package com.company.mentoring.view.workspacedashboard;


import com.company.mentoring.app.ApplicationAutoFillService;
import com.company.mentoring.app.WorkspaceParticipantService;
import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.Workspace;
import com.company.mentoring.entity.WorkspaceParticipant;
import com.company.mentoring.view.application.ApplicationStartView;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @ViewComponent("applicationsDataGrid.create")
    private CreateAction<Application> applicationCreateAction;

    @Autowired
    private DialogWindows dialogWindows;

    private UUID workspaceId;          // текущий воркспейс из URL

    @Subscribe
    public void onQueryParametersChange(QueryParametersChangeEvent event) {
        List<String> workspaceIds = event.getQueryParameters()
                .getParameters()
                .get("workspaceId");

        if (workspaceIds != null && !workspaceIds.isEmpty()) {
            workspaceId = UUID.fromString(workspaceIds.get(0));

            participantsDl.setParameter("workspaceId", workspaceId);
            applicationsDl.setParameter("workspaceId", workspaceId);

            participantsDl.load();
            applicationsDl.load();
        }
    }


    @Autowired
    private DataManager dataManager;

    @Autowired
    private ApplicationAutoFillService autoFillService;

    @Autowired
    private WorkspaceParticipantService workspaceParticipantService;

    private void openApplicationCreateDialog() {

        // 1) создаём новую Application
        Application application = dataManager.create(Application.class);

        // 2) ставим workspace СРАЗУ
        if (workspaceId == null) {
            throw new IllegalStateException("workspaceId is null");
        }

        Workspace workspace = dataManager.load(Workspace.class)
                .id(workspaceId)
                .one();

        application.setWorkspace(workspace);

        // 3) получаем инициатора
        WorkspaceParticipant initiator =
                workspaceParticipantService.getCurrentParticipant(workspace);

        // 4) автозаполнение
        autoFillService.autoFillApplication(application, initiator);

        // 5) открываем detail-view
        dialogWindows.detail(this, Application.class)
                .withViewClass(ApplicationStartView.class)
                .editEntity(application)
                .open();
    }

    @Subscribe(id = "createButton", subject = "clickListener")
    public void onCreateButtonClick(final ClickEvent<JmixButton> event) {
        openApplicationCreateDialog();
    }
}