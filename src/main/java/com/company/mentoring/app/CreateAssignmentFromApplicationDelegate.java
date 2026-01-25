package com.company.mentoring.app;

import com.company.mentoring.entity.*;
import io.jmix.core.DataManager;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("createAssignmentFromApplicationDelegate")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CreateAssignmentFromApplicationDelegate implements JavaDelegate {

    @Autowired
    private DataManager dataManager;

    @Override
    public void execute(DelegateExecution execution) {
        UUID applicationId = (UUID) execution.getVariable("applicationId");
        if (applicationId == null) {
            throw new IllegalStateException("applicationId is null");
        }

        System.out.println("текущая заявка: " + applicationId);

        Application app = dataManager.load(Application.class)
                .id(applicationId)
                .one();

        Assignment assignment = dataManager.create(Assignment.class);
//        assignment.setApplication(app);
        assignment.setTitle(app.getTitle());
        assignment.setWorkspace(app.getWorkspace());
        assignment.setDescription(app.getDescription());
        assignment.setStatus(AssignmentStatus.ACTIVE);

        dataManager.save(assignment);

        User approverUser = (User) execution.getVariable("approverUsername");
//        // 1. Берём существующую команду у заявки
//        WorkspaceTeam existingTeam = app.getTeams().stream()
//                .findFirst()
//                .orElseThrow(() ->
//                        new IllegalStateException("Application has no teams")
//                );
//
//        // 2. Определяем противоположный тип
//        TeamType oppositeType =
//                existingTeam.getTeamType() == TeamType.SUPERVISORS
//                        ? TeamType.EXECUTORS
//                        : TeamType.SUPERVISORS;
//
//        // 3. Создаём новую команду противоположного типа
//        WorkspaceTeam newTeam = dataManager.create(WorkspaceTeam.class);
//        newTeam.setApplication(app);
//        newTeam.setTeamType(oppositeType);
//
//        dataManager.save(newTeam);

        // 4. Находим WorkspaceParticipant для approverUser
        WorkspaceParticipant participant = dataManager.load(WorkspaceParticipant.class)
                .query("""
            select wp from WorkspaceParticipant wp
            where wp.workspace = :workspace
              and wp.user = :user
        """)
                .parameter("workspace", app.getWorkspace())
                .parameter("user", approverUser)
                .one();

        // 5. Добавляем участника в новую команду
//        WorkspaceTeamMember member = dataManager.create(WorkspaceTeamMember.class);
//        member.setTeam(newTeam);
//        member.setParticipant(participant);
//
//        dataManager.save(member);
//        app.getTeams().add(newTeam);
//
//        // 6. копируем команды из заявки в назначение
//        assignment.setExecutorsTeam(app.getTeams().stream()
//                .filter(t -> t.getTeamType() == TeamType.EXECUTORS)
//                .findFirst()
//                .orElse(null));
//
//        assignment.setSupervisorsTeam(app.getTeams().stream()
//                .filter(t -> t.getTeamType() == TeamType.SUPERVISORS)
//                .findFirst()
//                .orElse(null));

        dataManager.save(assignment);

        // сохраняем ID для следующего userTask
        execution.setVariable("assignmentId", assignment.getId());

    }
}
