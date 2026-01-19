package com.company.mentoring.view.application;

import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.ApplicationClarification;
import com.company.mentoring.entity.User;
import com.company.mentoring.entity.WorkspaceParticipant;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
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


@ViewController(id = "Application.comment")
@ViewDescriptor(path = "application-comment-view.xml")
@EditedEntityContainer("applicationDc")
@ProcessForm(
        outcomes = {
                @Outcome(id = "reply")
        },
        allowedProcessKeys = {"application-approval"}
)
public class ApplicationComment extends StandardDetailView<Application> {

    @ViewComponent
    private InstanceContainer<Application> applicationDc;
    @Autowired
    private DataManager dataManager;
    @ProcessVariable(name = "applicationId")
    private UUID applicationId;

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private TextArea commentField;
    @ViewComponent
    private CollectionContainer<ApplicationClarification> clarificationsDc;
    @Autowired
    private Notifications notifications;
    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        applicationDc.setItem(
                dataManager.load(Application.class)
                        .id(applicationId)
                        .one()
        );
    }

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

    @Subscribe(id = "replyCommentBtn")
    public void onReplyCommentBtnClick(final ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("reply")
                .complete();
        closeWithDefaultAction();
    }
}