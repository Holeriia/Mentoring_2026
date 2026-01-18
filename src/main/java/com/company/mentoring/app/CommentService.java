package com.company.mentoring.app;

import com.company.mentoring.entity.User;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("commentService")
public class CommentService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {

        User current = (User) execution.getVariable("assigneeUsername");
        User previous = (User) execution.getVariable("previousAssignee");

        execution.setVariable("assigneeUsername", previous);
        execution.setVariable("previousAssignee", current);

    }
}
