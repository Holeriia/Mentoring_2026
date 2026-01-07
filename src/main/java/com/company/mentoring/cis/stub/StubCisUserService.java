package com.company.mentoring.cis.stub;

import com.company.mentoring.cis.dto.ExternalUserDto;
import com.company.mentoring.cis.service.CisUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StubCisUserService implements CisUserService {

    @Override
    public List<ExternalUserDto> getAllUsers() {
        return List.of(
                createUser("cis-001","student1","Иван","Иванов","ivanov@test.ru","STUDENT"),
                createUser("cis-002","mentor1","Петр","Петров","petrov@test.ru","EMPLOYEE")
        );
    }

    private ExternalUserDto createUser(String externalId, String username, String firstName,
                                       String lastName, String email, String userType) {
        ExternalUserDto dto = new ExternalUserDto();
        dto.setExternalId(externalId);
        dto.setUsername(username);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setUserType(userType);
        return dto;
    }
}
