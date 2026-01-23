package com.company.mentoring.view.applicationpriority;

import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.ApplicationPriority;
import com.company.mentoring.entity.ContextRole;
import com.company.mentoring.entity.WorkspaceParticipant;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route(value = "application-priorities/:id", layout = MainView.class)
@ViewController(id = "ApplicationPriority.detail")
@ViewDescriptor(path = "application-priority-detail-view.xml")
@EditedEntityContainer("applicationPriorityDc")
public class ApplicationPriorityDetailView extends StandardDetailView<ApplicationPriority> {

    @Subscribe
    public void onInitEntity(InitEntityEvent<ApplicationPriority> event) {
        ApplicationPriority priority = event.getEntity();

        Application application = priority.getApplication();
        if (application == null) {
            return;
        }

        List<ApplicationPriority> priorities = application.getPriorities();
        if (priorities == null || priorities.isEmpty()) {
            priority.setPriorityNumber(1);
        } else {
            int max = priorities.stream()
                    .map(ApplicationPriority::getPriorityNumber)
                    .filter(Objects::nonNull)
                    .max(Integer::compareTo)
                    .orElse(0);

            priority.setPriorityNumber(max + 1);
        }
    }

    @ViewComponent
    private CollectionLoader<WorkspaceParticipant> participantsDl;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        ApplicationPriority priority = getEditedEntity();
        Application application = priority.getApplication();

        if (application == null || application.getInitiator() == null) {
            return;
        }

        // Загружаем initiator заново через DataManager, чтобы получить contextRole и workspace
        WorkspaceParticipant initiator = dataManager.load(WorkspaceParticipant.class)
                .id(application.getInitiator().getId())
                .one();

        ContextRole initiatorRole = initiator.getContextRole();
        if (initiatorRole == null) return;

        participantsDl.setParameter("workspace", initiator.getWorkspace());
        participantsDl.setParameter("role", oppositeRole(initiatorRole).getId());

        participantsDl.load();
    }

    private ContextRole oppositeRole(ContextRole role) {
        if (role == null) {
            return null;
        }
        return role == ContextRole.EXECUTOR
                ? ContextRole.SUPERVISOR
                : ContextRole.EXECUTOR;
    }
}