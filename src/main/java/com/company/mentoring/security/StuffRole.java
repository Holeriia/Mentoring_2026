package com.company.mentoring.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "StuffRole", code = StuffRole.CODE)
public interface StuffRole {
    String CODE = "stuff-role";
}