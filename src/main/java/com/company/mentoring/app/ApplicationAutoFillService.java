package com.company.mentoring.app;

import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.ApplicationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAutoFillService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationAutoFillService.class);

    public void autoFillApplication(Application application) {
        if (application.getStatus() == null) {
            application.setStatus(ApplicationStatus.DRAFT);
        }

        if (application.getCurrentPriorityIndex() == null) {
            application.setCurrentPriorityIndex(1);
        }

        log.debug("Автозаполнение новой Application: status={}, currentPriorityIndex={}",
                application.getStatus(), application.getCurrentPriorityIndex());
    }
}