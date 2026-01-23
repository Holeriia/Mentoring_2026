 package com.company.mentoring.view.application;

 import com.company.mentoring.app.ApplicationProcessService;
 import com.company.mentoring.entity.Application;
 import com.vaadin.flow.component.ClickEvent;
 import io.jmix.flowui.kit.component.button.JmixButton;
 import io.jmix.flowui.model.DataContext;
 import io.jmix.flowui.view.*;
 import org.springframework.beans.factory.annotation.Autowired;

@ViewController(id = "Application.detail")
@ViewDescriptor(path = "application-start-view.xml")
@EditedEntityContainer("applicationDc")
 public class ApplicationStartView extends StandardDetailView<Application> {

     @Autowired
     private ApplicationProcessService applicationProcessService;

     @Subscribe("startProcessBtn")
     public void onStartProcessBtnClick(ClickEvent<JmixButton> event) {
         // Получаем DataContext текущего экрана
         var dataContext = getViewData().getDataContext();
         // Берём текущую редактируемую сущность
         Application application = getEditedEntity();
         // Помещаем сущность в DataContext (если ещё не отслеживается)
         Application trackedApplication = dataContext.merge(application);
         // Сохраняем сущность и её композиции
         dataContext.save();
         // Запускаем процесс
         applicationProcessService.startProcess(trackedApplication);
         // Закрываем окно
         closeWithDefaultAction();
     }
 }