package com.company.mentoring.cis.service;

import com.company.mentoring.cis.dto.ExternalUserDto;

import java.util.List;

public interface CisUserService {

    List<ExternalUserDto> getAllUsers();
}
