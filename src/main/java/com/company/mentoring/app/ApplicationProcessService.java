package com.company.mentoring.app;

import com.company.mentoring.entity.*;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationProcessService {

    private final RuntimeService runtimeService;

    public ApplicationProcessService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void startProcess(Application application) {

        ApplicationPriority firstPriority = application.getPriorities()
                .stream()
                .sorted(Comparator.comparing(ApplicationPriority::getPriorityNumber))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Нет приоритетов у заявки")
                );

        WorkspaceParticipant recipient = firstPriority.getParticipant();

        application.setCurrentRecipient(recipient);
        application.setCurrentPriorityIndex(firstPriority.getPriorityNumber());
        application.setStatus(ApplicationStatus.IN_REVIEW);

        User assigneeUser = recipient.getUser();

        Map<String, Object> variables = new HashMap<>();
        variables.put("assigneeUsername", assigneeUser); // <-- User
        variables.put("applicationId", application.getId());

        ProcessInstance pi = runtimeService.startProcessInstanceByKey(
                "application-approval",
                application.getId().toString(),
                variables
        );

        application.setProcessInstanceId(pi.getId());
    }
}
