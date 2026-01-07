package com.company.mentoring.user;

import com.company.mentoring.cis.dto.ExternalUserDto;
import com.company.mentoring.entity.User;
import com.company.mentoring.security.FullAccessRole;
import com.company.mentoring.security.MemberRole;
import com.company.mentoring.security.StuffRole;
import com.company.mentoring.security.UiMinimalRole;
import io.jmix.core.DataManager;
import io.jmix.security.role.assignment.RoleAssignment;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserProvisioningService {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private RoleAssignmentRepository roleAssignmentRepository;

    public void importUsers(List<ExternalUserDto> users) {
        for (ExternalUserDto dto : users) {
            User user = dataManager.load(User.class)
                    .query("select u from User u where u.username = :username")
                    .parameter("username", dto.getUsername())
                    .optional()
                    .orElseGet(() -> createUser(dto));

            updateUser(user, dto);
            assignRoles(user, dto);
            dataManager.save(user);
        }
    }

    private User createUser(ExternalUserDto dto) {
        User user = dataManager.create(User.class);
        user.setUsername(dto.getUsername());
        user.setActive(true);
        return user;
    }

    private void updateUser(User user, ExternalUserDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
    }

    private void assignRoles(User user, ExternalUserDto dto) {
//        // удаляем старые роли
//        Collection<RoleAssignment> oldAssignments = roleAssignmentRepository.getAssignmentsByUsername(user.getUsername());
//        for (RoleAssignment ra : oldAssignments) {
//            roleAssignmentRepository.remove(ra);
//        }
//
//        // добавляем UI минимальный
//        roleAssignmentRepository.assignResourceRole(UiMinimalRole.CODE, user.getUsername());
//
//        // добавляем роль по типу пользователя
//        switch (dto.getUserType()) {
//            case "STUDENT" -> roleAssignmentRepository.assignResourceRole(MemberRole.CODE, user.getUsername());
//            case "EMPLOYEE" -> roleAssignmentRepository.assignResourceRole(StuffRole.CODE, user.getUsername());
//            case "ADMIN" -> roleAssignmentRepository.assignResourceRole(FullAccessRole.CODE, user.getUsername());
//        }
    }
}

