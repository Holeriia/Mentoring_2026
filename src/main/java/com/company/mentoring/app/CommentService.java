package com.company.mentoring.app;

import com.company.mentoring.entity.User;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("commentService")
public class CommentService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Сервис комментариев запущен");

        User current = (User) execution.getVariable("assigneeUsername");
        User previous = (User) execution.getVariable("previousAssignee");

        execution.setVariable("assigneeUsername", previous);
        execution.setVariable("previousAssignee", current);

        System.out.println("Возврат задачи пользователю: " + previous);
    }
}
