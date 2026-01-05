package com.company.mentoring.app;

import com.company.mentoring.entity.Application;
import com.company.mentoring.entity.ApplicationPriority;
import com.company.mentoring.entity.ApplicationStatus;
import com.company.mentoring.entity.WorkspaceParticipant;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Component("moveToNextRecipientDelegate")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MoveToNextRecipientDelegate implements JavaDelegate {

    @Autowired
    private DataManager dataManager;

    @Override
    public void execute(DelegateExecution execution) {

        System.out.println("\n\nПереходим к следующему");

        UUID applicationId = (UUID) execution.getVariable("applicationId");

        // 1. Загружаем заявку
        Application app = dataManager.load(Application.class)
                .id(applicationId)
                .one();

        Integer currentIndex = app.getCurrentPriorityIndex();
        if (currentIndex == null) {
            currentIndex = 0;
        }

        System.out.println("\nТекущий индекс (из Application): " + currentIndex);

        // 2. Загружаем приоритеты
        List<ApplicationPriority> priorities = dataManager.load(ApplicationPriority.class)
                .query("""
                       select p from ApplicationPriority p
                       where p.application = :app
                         and p.priorityNumber is not null
                       """)
                .parameter("app", app)
                .list();

        priorities.sort(Comparator.comparing(ApplicationPriority::getPriorityNumber));

        // 3. Если приоритетов больше нет → финальный отказ
        if (currentIndex >= priorities.size()) {

            System.out.println(
                    "\nЗаявка " + app.getId() +
                            " отклонена всеми участниками. Финальный отказ."
            );

            app.setStatus(ApplicationStatus.FINAL_REJECTED);
            app.setCurrentRecipient(null);
            dataManager.save(app);

            execution.setVariable("hasNext", false);
            return;
        }

        // 4. Берём следующего участника
        ApplicationPriority nextPriority = priorities.get(currentIndex);
        WorkspaceParticipant participant = nextPriority.getParticipant();

        if (participant == null || participant.getUser() == null) {
            throw new IllegalStateException("У следующего участника не задан пользователь");
        }

        System.out.println("\nПереходим к следующему: " + participant.getUser());

        // 5. Обновляем заявку
        app.setCurrentRecipient(participant);
        app.setCurrentPriorityIndex(currentIndex + 1);
        dataManager.save(app);

        // 6. Обновляем переменные процесса
        execution.setVariable("hasNext", true);
        execution.setVariable("currentPriorityIndex", currentIndex + 1);
        execution.setVariable("assigneeUsername", participant.getUser()); // как ты и просила
    }
}

