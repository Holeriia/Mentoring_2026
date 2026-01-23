package com.company.mentoring.view.assignmentcreating;


import com.company.mentoring.entity.Assignment;
import com.company.mentoring.entity.AssignmentStatus;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.core.DataManager;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.model.InstanceLoader;
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

    @ViewComponent
    private InstanceLoader<Assignment> assignmentDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (assignmentId == null) {
            return;
        }
        assignmentDl.setEntityId(assignmentId);
        assignmentDl.load();
    }

    @Subscribe("approveBtn")
    protected void onApproveBtnClick(ClickEvent<JmixButton> event) {

        Assignment assignment = assignmentDc.getItem();
        // меняем статус заявки
        assignment.setStatus(AssignmentStatus.CLOSED);
        // сохраняем заявку
        dataManager.save(assignment);

        // завершаем BPM-задачу с outcome approve
        processFormContext.taskCompletion()
                .withOutcome("approve")
                .complete();

        closeWithDefaultAction();
    }

    @Subscribe("cancelBtn")
    protected void onCancelBtnClick(ClickEvent<JmixButton> event) {
        closeWithDefaultAction();
    }
}