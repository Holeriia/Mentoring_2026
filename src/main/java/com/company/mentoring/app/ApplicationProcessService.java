package com.company.mentoring.app;

import com.company.mentoring.entity.*;
import io.jmix.core.DataManager;
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
    private final DataManager dataManager;

    public ApplicationProcessService(RuntimeService runtimeService,
                                     DataManager dataManager) {
        this.runtimeService = runtimeService;
        this.dataManager = dataManager;
    }

    public void startProcess(Application application) {

        Application app = dataManager.load(Application.class)
                .id(application.getId())
                .fetchPlan(builder -> {
                    builder.addFetchPlan("_base");

                    builder.add("initiator", ip -> {
                        ip.add("contextRole");
                        ip.add("user");
                    });

                    builder.add("priorities", p -> {
                        p.add("priorityNumber");
                        p.add("participant", wp -> {
                            wp.add("user");
                        });
                    });
                })
                .one();

        ApplicationPriority firstPriority = app.getPriorities()
                .stream()
                .sorted(Comparator.comparing(ApplicationPriority::getPriorityNumber))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Нет приоритетов у заявки")
                );

        WorkspaceParticipant recipient = firstPriority.getParticipant();

        app.setCurrentRecipient(recipient);
        app.setCurrentPriorityIndex(firstPriority.getPriorityNumber());
        app.setStatus(ApplicationStatus.IN_REVIEW);

        User assigneeUser = recipient.getUser();
        User initiatorUser = app.getInitiator().getUser();

        boolean initiatorIsSupervisor =
                app.getInitiator().getContextRole() == ContextRole.SUPERVISOR;

        Map<String, Object> variables = new HashMap<>();
        variables.put("assigneeUsername", assigneeUser);
        variables.put("previousAssignee", initiatorUser);
        variables.put("applicationId", app.getId());
        variables.put("initiatorIsSupervisor", initiatorIsSupervisor);

        ProcessInstance pi = runtimeService.startProcessInstanceByKey(
                "application-approval",
                app.getId().toString(),
                variables
        );

        app.setProcessInstanceId(pi.getId());
        dataManager.save(app);
    }
}
