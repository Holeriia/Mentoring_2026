package com.company.mentoring.service;

import com.company.mentoring.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.flowui.model.DataContext;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamManagementService {

    private final DataManager dataManager;
    private final Metadata metadata;

    public TeamManagementService(DataManager dataManager, Metadata metadata) {
        this.dataManager = dataManager;
        this.metadata = metadata;
    }

    /**
     * Создает команду специально для инициатора заявки.
     */
    public Team createTeamForInitiator(
            Application application,
            DataContext dataContext
    ) {
        WorkspaceParticipant initiator = application.getInitiator();

        TeamType type = initiator.getContextRole() == ContextRole.EXECUTOR
                ? TeamType.EXECUTORS
                : TeamType.SUPERVISORS;

        Team team = dataContext.create(Team.class);
        team.setApplication(application);
        team.setTeamType(type);

        TeamMember member = dataContext.create(TeamMember.class);
        member.setTeam(team);
        member.setParticipant(initiator);

        team.setMembers(List.of(member));
        return team;
    }


    /**
     * Создает пустую команду заданного типа для заявки.
     */
    public Team createEmptyTeam(Application application, TeamType type) {
        Team team = metadata.create(Team.class);
        team.setApplication(application);
        team.setTeamType(type);
        team.setMembers(new ArrayList<>());
        return team;
    }

    /**
     * Внутренний метод для сборки команды с участником
     */
    private Team createTeamWithMember(Application application, TeamType type, WorkspaceParticipant participant) {
        Team team = createEmptyTeam(application, type);

        TeamMember member = metadata.create(TeamMember.class);
        member.setTeam(team);
        member.setParticipant(participant);

        team.getMembers().add(member);
        return team;
    }

    /**
     * Возвращает подходящих участников из пространства по типу команды
     */
    public List<WorkspaceParticipant> getAvailableParticipants(Application application, TeamType teamType) {
        if (application.getWorkspace() == null) return List.of();

        String roleId = TeamType.EXECUTORS.equals(teamType)
                ? ContextRole.EXECUTOR.getId()
                : ContextRole.SUPERVISOR.getId();

        return dataManager.load(WorkspaceParticipant.class)
                .query("select p from WorkspaceParticipant p where p.workspace = :ws and p.contextRole = :role")
                .parameter("ws", application.getWorkspace())
                .parameter("role", roleId)
                .list();
    }
}