package com.company.mentoring.view.applicationapproval;

import com.company.mentoring.entity.Application;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@ViewController("Application.approval")
@ViewDescriptor("application-approval-view.xml")
@ProcessForm(
        outcomes = {
                @Outcome(id = "approve"),
                @Outcome(id = "reject")
        },
        allowedProcessKeys = {"application-approval"}
)
public class ApplicationApprovalView extends StandardView {

        @Autowired
        private DataManager dataManager;

        @ViewComponent
        private InstanceContainer<Application> applicationDc;

        @Autowired
        private ProcessFormContext processFormContext;

        // process variable из стартовой формы
        @ProcessVariable(name = "applicationId")
        private UUID applicationId;

        @Autowired
        private CurrentAuthentication currentAuthentication;

        @Subscribe
        public void onBeforeShow(BeforeShowEvent event) {
                if (applicationId == null) {
                        System.out.println("ApplicationId is null!");
                        return;
                }

                Application application = dataManager.load(Application.class)
                        .id(applicationId)
                        .one();

                if (application == null) {
                        System.out.println("Application not found!");
                        return;
                }

                applicationDc.setItem(application);
                System.out.println("Application loaded: " + application.getTitle());
        }


        @Subscribe("approveBtn")
        protected void onApproveBtnClick(ClickEvent<JmixButton> event) {
                String username = currentAuthentication.getUser().getUsername();
                processFormContext.taskCompletion()
                        .withOutcome("approve")
                        .addProcessVariable("approverUsername", username)
                        .complete();
                closeWithDefaultAction();
        }

        @Subscribe("rejectBtn")
        protected void onRejectBtnClick(ClickEvent<JmixButton> event) {
                processFormContext.taskCompletion()
                        .withOutcome("reject")
                        .complete();
                closeWithDefaultAction();
        }
}