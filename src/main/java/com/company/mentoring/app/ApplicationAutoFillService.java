package com.company.mentoring.app;

import com.company.mentoring.entity.*;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationAutoFillService {

    @Autowired
    private DataManager dataManager;

    public void createInitialTeam(Application application, WorkspaceParticipant participant) {
        WorkspaceTeam workspaceTeam = dataManager.create(WorkspaceTeam.class);

        // привязываем к заявке
//        workspaceTeam.setApplication(application);

        // тип команды совпадает с ролью участника
        workspaceTeam.setTeamType(TeamType.fromContextRole(participant.getContextRole()));

        // создаём участника команды
        WorkspaceTeamMember member = dataManager.create(WorkspaceTeamMember.class);
        member.setTeam(workspaceTeam);
        member.setParticipant(participant);

        // добавляем в команду
        workspaceTeam.setMembers(List.of(member));

        // добавляем команду к заявке
//        application.setTeams(List.of(workspaceTeam));
    }

    public void autoFillApplication(Application application,
                                    WorkspaceParticipant participant) {

        if (application.getStatus() == null) {
            application.setStatus(ApplicationStatus.DRAFT);
        }

        if (application.getCurrentPriorityIndex() == null) {
            application.setCurrentPriorityIndex(1);
        }

        if (application.getInitiator() == null) {
            application.setInitiator(participant);
        }

        // создаём команду с инициатором
        createInitialTeam(application, participant);
    }
}