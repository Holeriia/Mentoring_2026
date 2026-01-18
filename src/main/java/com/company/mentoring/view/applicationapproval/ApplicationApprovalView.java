package com.company.mentoring.view.applicationapproval;

import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.ApplicationClarification;
import com.company.mentoring.entity.User;
import com.company.mentoring.entity.WorkspaceParticipant;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.textfield.TextArea;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

@ViewController("Application.approval")
@ViewDescriptor("application-approval-view.xml")
@ProcessForm(
        outcomes = {
                @Outcome(id = "approve"),
                @Outcome(id = "reject"),
                @Outcome(id = "comment")
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
                applicationDc.setItem(
                        dataManager.load(Application.class)
                                .id(applicationId)
                                .one()
                );
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

        @Subscribe(id = "commentBtn")
        public void onCommentBtnClick(final ClickEvent<JmixButton> event) {
                processFormContext.taskCompletion()
                        .withOutcome("comment")
                        .complete();
                closeWithDefaultAction();
        }


        @ViewComponent
        private TextArea commentField;
        @ViewComponent
        private CollectionContainer<ApplicationClarification> clarificationsDc;
        @Autowired
        private Notifications notifications;

        @Subscribe(id = "addCommentBtn")
        public void onAddCommentBtnClick(final ClickEvent<JmixButton> event) {
                String text = commentField.getValue();
                if (text == null || text.isBlank()) {
                        notifications.create("Введите текст комментария")
                                .withType(Notifications.Type.WARNING)
                                .show();
                        return;
                }

                Application application = applicationDc.getItem();
                User currentUser = (User) currentAuthentication.getUser();

                WorkspaceParticipant author = dataManager.load(WorkspaceParticipant.class)
                        .query("select wp from WorkspaceParticipant wp where wp.user.id = :userId")
                        .parameter("userId", currentUser.getId())
                        .optional()
                        .orElseThrow(() -> new IllegalStateException("WorkspaceParticipant not found for current user"));

                ApplicationClarification clarification = dataManager.create(ApplicationClarification.class);
                clarification.setApplication(application);
                clarification.setAuthor(author);
                clarification.setMessage(text);
                clarification.setCreatedAt(new Date());

                dataManager.save(clarification);

                clarificationsDc.getMutableItems().add(clarification);
                commentField.clear();
        }
}