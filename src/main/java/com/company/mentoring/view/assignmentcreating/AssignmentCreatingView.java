package com.company.mentoring.view.assignmentcreating;


import com.company.mentoring.entity.Assignment;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.core.DataManager;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@ViewController("Assignment.creating")
@ViewDescriptor("assignment-creating-view.xml")
@ProcessForm(
        outcomes = {
                @Outcome(id = "approve"),
                @Outcome(id = "cancel")
        },
        allowedProcessKeys = {"application-approval"}
)
public class AssignmentCreatingView extends StandardView {

    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private InstanceContainer<Assignment> assignmentDc;

    @Autowired
    private ProcessFormContext processFormContext;

    @ProcessVariable(name = "assignmentId")
    private UUID assignmentId;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (assignmentId == null) return;

        Assignment assignment = dataManager.load(Assignment.class)
                .id(assignmentId)
                .one();

        assignmentDc.setItem(assignment);
    }

    @Subscribe("approveBtn")
    protected void onApproveBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("approve")
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe("cancelBtn")
    protected void onCancelBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("cancel")
                .complete();
        closeWithDefaultAction();
    }
}