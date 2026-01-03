package com.company.mentoring.view.workspace;

import com.company.mentoring.entity.User;
import com.company.mentoring.entity.Workspace;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "workspaces/:id", layout = MainView.class)
@ViewController(id = "Workspace.detail")
@ViewDescriptor(path = "workspace-detail-view.xml")
@EditedEntityContainer("workspaceDc")
public class WorkspaceDetailView extends StandardDetailView<Workspace> {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Workspace> event) {
        // Устанавливаем текущего пользователя владельцем при создании новой сущности
        User currentUser = (User) currentAuthentication.getUser();
        event.getEntity().setOwner(currentUser);
    }
}