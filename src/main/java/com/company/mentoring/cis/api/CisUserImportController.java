package com.company.mentoring.cis.api;

import com.company.mentoring.cis.service.CisUserService;
import com.company.mentoring.user.UserProvisioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cis/users")
public class CisUserImportController {

    @Autowired
    private CisUserService cisUserService;

    @Autowired
    private UserProvisioningService userProvisioningService;

    @PostMapping("/import")
    public String importUsers() {
        var users = cisUserService.getAllUsers();
        userProvisioningService.importUsers(users);
        return "OK";
    }
}
