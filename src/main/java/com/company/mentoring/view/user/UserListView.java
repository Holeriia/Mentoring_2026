package com.company.mentoring.view.user;

import com.company.mentoring.cis.dto.ExternalUserDto;
import com.company.mentoring.cis.stub.StubCisUserService;
import com.company.mentoring.entity.User;
import com.company.mentoring.user.UserProvisioningService;
import com.company.mentoring.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "users", layout = MainView.class)
@ViewController(id = "User.list")
@ViewDescriptor(path = "user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @Autowired
    private UserProvisioningService userProvisioningService;

    @Autowired
    private StubCisUserService stubCisUserService;

    @Autowired
    private Notifications notifications;

    @Subscribe(id = "importFromCISButton", subject = "clickListener")
    public void onImportFromCISButtonClick(final ClickEvent<JmixButton> event) {
        // Получаем пользователей из stub
        List<ExternalUserDto> users = stubCisUserService.getAllUsers();

        // Импортируем через сервис
        userProvisioningService.importUsers(users);

        notifications.create("Импорт завершён").show();
    }
}