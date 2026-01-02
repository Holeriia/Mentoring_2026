package com.company.mentoring.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum ApplicationStatus implements EnumClass<String> {

    DRAFT("D"),
    IN_REVIEW("IR"),
    CLARIFICATION("C"),
    ACCEPTED("A"),
    FINAL_REJECTED("FR");

    private final String id;

    ApplicationStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationStatus fromId(String id) {
        for (ApplicationStatus at : ApplicationStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}