package com.company.mentoring.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum TeamType implements EnumClass<String> {

    EXECUTORS("E"),
    SUPERVISORS("S");

    private final String id;

    TeamType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TeamType fromId(String id) {
        for (TeamType at : TeamType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }

    public static TeamType fromContextRole(ContextRole role) {
        switch (role) {
            case EXECUTOR: return EXECUTORS;
            case SUPERVISOR: return SUPERVISORS;
            default: throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}