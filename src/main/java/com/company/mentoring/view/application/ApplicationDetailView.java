 package com.company.mentoring.view.application;

import com.company.mentoring.entity.*;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


 @Route(value = "applications/:id", layout = MainView.class)
@ViewController(id = "Application.detail")
@ViewDescriptor(path = "application-detail-view.xml")
@EditedEntityContainer("applicationDc")
public class ApplicationDetailView extends StandardDetailView<Application> {

    @Autowired
    private RuntimeService runtimeService;


     @Subscribe("startProcessBtn")
     public void onStartProcessBtnClick(final ClickEvent<JmixButton> event) {

         Application application = getEditedEntity();

         ApplicationPriority firstPriority = application.getPriorities()
                 .stream()
                 .sorted(Comparator.comparing(ApplicationPriority::getPriorityNumber))
                 .findFirst()
                 .orElseThrow(() -> new IllegalStateException("Нет приоритетов у заявки"));

         WorkspaceParticipant recipient = firstPriority.getParticipant();
         application.setCurrentRecipient(recipient);
         application.setCurrentPriorityIndex(firstPriority.getPriorityNumber());
         application.setStatus(ApplicationStatus.IN_REVIEW);

         // ВАЖНО: передаём USER, а не username
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
         System.out.println("Процесс запущен, исполнитель: " + assigneeUser.getUsername());
     }
}