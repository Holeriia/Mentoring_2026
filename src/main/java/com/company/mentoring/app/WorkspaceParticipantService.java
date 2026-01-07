package com.company.mentoring.app;

import com.company.mentoring.entity.User;
import com.company.mentoring.entity.Workspace;
import com.company.mentoring.entity.WorkspaceParticipant;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceParticipantService {

    private final DataManager dataManager;
    private final CurrentAuthentication currentAuthentication;

    public WorkspaceParticipantService(DataManager dataManager,
                                       CurrentAuthentication currentAuthentication) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
    }

    public WorkspaceParticipant getCurrentParticipant(Workspace workspace) {
        User user = (User) currentAuthentication.getUser();

        return dataManager.load(WorkspaceParticipant.class)
                .query("""
                    select wp from WorkspaceParticipant wp
                    where wp.user = :user
                      and wp.workspace = :workspace
                """)
                .parameter("user", user)
                .parameter("workspace", workspace)
                .one();
    }
}
