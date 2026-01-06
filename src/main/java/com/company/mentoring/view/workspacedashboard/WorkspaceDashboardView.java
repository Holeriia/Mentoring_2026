package com.company.mentoring.view.workspacedashboard;


import com.company.mentoring.app.ApplicationAutoFillService;
import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.Workspace;
import com.company.mentoring.entity.WorkspaceParticipant;
import com.company.mentoring.view.application.ApplicationDetailView;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
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

    private void openApplicationCreateDialog() {
        // 1) создаём новый инстанс Application
        Application application = dataManager.create(Application.class);

        // 2) автозаполнение через сервис
        autoFillService.autoFillApplication(application);
        System.out.println("айди " + workspaceId);

        // 3) ставим workspace сразу здесь
        if (workspaceId != null) {
            Workspace workspace = dataManager.load(Workspace.class)
                    .id(workspaceId)
                    .one();
            application.setWorkspace(workspace);
        }

        // 4) открываем detail‑вью с УЖЕ заполняющейся сущностью
        dialogWindows.detail(this, Application.class)
                .withViewClass(ApplicationDetailView.class)
                .editEntity(application)   // <-- вместо newEntity()
                .open();
    }

    @Subscribe(id = "createButton", subject = "clickListener")
    public void onCreateButtonClick(final ClickEvent<JmixButton> event) {
        System.out.println("click!");
        openApplicationCreateDialog();
    }
}