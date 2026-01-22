package com.company.mentoring.view.workspacedashboard;


import com.company.mentoring.app.ApplicationAutoFillService;
import com.company.mentoring.app.WorkspaceParticipantService;
import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.Workspace;
import com.company.mentoring.entity.WorkspaceParticipant;
import com.company.mentoring.view.application.ApplicationStartView;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.bpm.entity.TaskData;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.multitenancy.BpmTenantProvider;
import io.jmix.bpm.service.UserGroupService;
import io.jmix.bpm.util.FlowableEntitiesConverter;
import io.jmix.bpmflowui.processform.ProcessFormViews;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.Messages;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.apache.commons.collections4.CollectionUtils;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
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

    @ViewComponent
    private CollectionLoader<TaskData> tasksDl;

    @ViewComponent
    private CollectionContainer<TaskData> tasksDc;

    @ViewComponent
    private DataGrid<TaskData> tasksDataGrid;

    // Flowable & BPM API
    @Autowired
    private TaskService taskService;
    @Autowired
    private FlowableEntitiesConverter entitiesConverter;
    @Autowired(required = false)
    private BpmTenantProvider bpmTenantProvider;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    private String currentUserName;
    private List<String> userGroupCodes;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        currentUserName = currentUserSubstitution.getEffectiveUser().getUsername();
        userGroupCodes = userGroupService.getUserGroups(currentUserName)
                .stream()
                .map(UserGroup::getCode)
                .toList();

        tasksDl.load();
    }

    @Install(to = "tasksDl", target = Target.DATA_LOADER)
    private List<TaskData> tasksDlLoadDelegate(LoadContext<TaskData> loadContext) {
        TaskQuery taskQuery = taskService.createTaskQuery().active();
        addAssignmentCondition(taskQuery);
        long count = taskQuery.count();
        taskQuery.orderByTaskCreateTime().desc();
        List<Task> tasks = taskQuery.list();

        List<TaskData> result = tasks.stream()
                .map(entitiesConverter::createTaskData)
                .toList();

        return result;
    }

    @Autowired
    private ProcessFormViews processFormViews;

    @Subscribe("tasksDataGrid.openTaskForm")
    private void onTasksDataGridOpenTaskForm(ActionPerformedEvent event) {
        TaskData selected = tasksDc.getItemOrNull();
        if (selected == null) {
            return;
        }

        Task task = taskService.createTaskQuery()
                .taskId(selected.getId())
                .singleResult();

        if (task == null) {
            return;
        }

        processFormViews.openTaskProcessForm(task, this, dialog ->
                dialog.addAfterCloseListener(afterClose -> tasksDl.load())
        );
    }

    private void addAssignmentCondition(TaskQuery taskQuery) {
        taskQuery.or();

        // пользователь исполнитель или кандидат
        taskQuery.taskCandidateOrAssigned(currentUserName);

        // плюс задачи по его группам
        if (userGroupCodes != null && !userGroupCodes.isEmpty()) {
            taskQuery.taskCandidateGroupIn(userGroupCodes);
        }

        taskQuery.endOr();

        if (bpmTenantProvider != null && bpmTenantProvider.isMultitenancyActive()) {
            taskQuery.taskTenantId(bpmTenantProvider.getCurrentUserTenantId());
        }
    }


    /**
     * для перевода названия задач
     */
    @Autowired
    private Messages messages;

    @Supply(to = "tasksDataGrid.name", subject = "renderer")
    protected Renderer<TaskData> taskNameRenderer() {
        return new ComponentRenderer<>(taskData -> {
            String rawName = taskData.getName(); // "task.approve", "task.replyComment", ...
            if (rawName == null) {
                return new Span("");
            }

            String localized = messages.getMessage("com.company.mentoring.bpm", rawName);
            // Messages.getMessage(...) никогда не возвращает null — если нет ключа, вернёт rawName
            return new Span(localized);
        });
    }

}