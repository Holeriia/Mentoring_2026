package com.company.mentoring.app;

import com.company.mentoring.entity.*;
import io.jmix.core.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationAutoFillService {

    @Autowired
    private DataManager dataManager;

    public void createInitialTeam(Application application, WorkspaceParticipant participant) {
        Team team = dataManager.create(Team.class);

        // привязываем к заявке
        team.setApplication(application);

        // тип команды совпадает с ролью участника
        team.setTeamType(TeamType.fromContextRole(participant.getContextRole()));

        // создаём участника команды
        TeamMember member = dataManager.create(TeamMember.class);
        member.setTeam(team);
        member.setParticipant(participant);

        // добавляем в команду
        team.setMembers(List.of(member));

        // добавляем команду к заявке
        application.setTeams(List.of(team));
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