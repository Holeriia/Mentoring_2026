package com.company.mentoring.service;

import com.company.mentoring.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.model.DataContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationInitializationService {

    private final DataManager dataManager;
    private final CurrentAuthentication currentAuthentication;
    private final TeamManagementService teamService;

    public ApplicationInitializationService(
            DataManager dataManager,
            CurrentAuthentication currentAuthentication,
            TeamManagementService teamService
    ) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
        this.teamService = teamService;
    }

    public void initializeNewApplication(
            Application application,
            DataContext dataContext
    ) {
        User user = (User) currentAuthentication.getUser();

        WorkspaceParticipant participant = dataManager.load(WorkspaceParticipant.class)
                .query("""
                    select p from WorkspaceParticipant p
                    where p.workspace = :ws and p.user = :user
                """)
                .parameter("ws", application.getWorkspace())
                .parameter("user", user)
                .one();

        application.setInitiator(participant);

        Team team = teamService.createTeamForInitiator(application, dataContext);
        application.setTeams(List.of(team));
    }
}
