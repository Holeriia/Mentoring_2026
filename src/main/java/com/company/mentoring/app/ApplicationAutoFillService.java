package com.company.mentoring.app;

import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.ApplicationStatus;
import com.company.mentoring.entity.WorkspaceParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAutoFillService {

    public void autoFillApplication(Application application,
                                    WorkspaceParticipant initiator) {

        if (application.getStatus() == null) {
            application.setStatus(ApplicationStatus.DRAFT);
        }

        if (application.getCurrentPriorityIndex() == null) {
            application.setCurrentPriorityIndex(1);
        }

        if (application.getInitiator() == null) {
            application.setInitiator(initiator);
        }
    }
}