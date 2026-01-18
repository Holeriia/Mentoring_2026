package com.company.mentoring.app;

import com.company.mentoring.entity.User;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("resolveApproverDelegate")
public class ResolveApproverDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {

        Boolean initiatorIsSupervisor =
                (Boolean) execution.getVariable("initiatorIsSupervisor");

        User initiatorUsername =
                (User) execution.getVariable("initiator");

        User approverUsername =
                (User) execution.getVariable("approverUsername");

        if (Boolean.TRUE.equals(initiatorIsSupervisor)) {
            // если инициатор — руководитель, он и есть approver
            execution.setVariable("approverUsername", initiatorUsername);
        } else {
            // иначе оставляем того, кто пришёл из approve
            execution.setVariable("approverUsername", approverUsername);
        }
    }
}