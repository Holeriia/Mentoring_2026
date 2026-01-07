package com.company.mentoring.app;

import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.Assignment;
import com.company.mentoring.entity.AssignmentStatus;
import com.company.mentoring.entity.TeamType;
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
        assignment.setApplication(app);
        assignment.setTitle(app.getTitle());
        assignment.setDescription(app.getDescription());
        assignment.setStatus(AssignmentStatus.ACTIVE);

        // команды можно скопировать так
        assignment.setExecutorsTeam(app.getTeams().stream()
                .filter(t -> t.getTeamType() == TeamType.EXECUTORS)
                .findFirst()
                .orElse(null));

        assignment.setSupervisorsTeam(app.getTeams().stream()
                .filter(t -> t.getTeamType() == TeamType.SUPERVISORS)
                .findFirst()
                .orElse(null));

        dataManager.save(assignment);

        // сохраняем ID для следующего userTask
//        execution.setVariable("assignmentId", assignment.getId());
    }
}
