package com.company.mentoring.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum WorkspaceStatus implements EnumClass<String> {

    DRAFT("D"),
    ACTIVE("A"),
    CLOSED("C");

    private final String id;

    WorkspaceStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static WorkspaceStatus fromId(String id) {
        for (WorkspaceStatus at : WorkspaceStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}