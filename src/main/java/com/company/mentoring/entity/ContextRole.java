package com.company.mentoring.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum ContextRole implements EnumClass<String> {

    EXECUTOR("E"),
    SUPERVISOR("S");

    private final String id;

    ContextRole(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ContextRole fromId(String id) {
        for (ContextRole at : ContextRole.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}