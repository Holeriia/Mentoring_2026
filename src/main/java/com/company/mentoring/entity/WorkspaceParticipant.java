package com.company.mentoring.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "WORKSPACE_PARTICIPANT", indexes = {
        @Index(name = "IDX_WORKSPACE_PARTICIPANT_WORKSPACE", columnList = "WORKSPACE_ID"),
        @Index(name = "IDX_WORKSPACE_PARTICIPANT_USER", columnList = "USER_ID")
})
@Entity
public class WorkspaceParticipant {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "WORKSPACE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "CONTEXT_ROLE")
    private String contextRole;

    @Column(name = "MAX_ASSIGNMENTS")
    private Integer maxAssignments;

    @InstanceName
    @DependsOnProperties({"user"})
    public String getInstanceName() {
        if (user == null) return "";

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String username = user.getUsername();

        if (firstName == null && lastName == null && username == null) {
            return user.getUsername(); // fallback
        }

        return String.format("%s %s %s",
                firstName != null ? firstName : "",
                lastName != null ? lastName : "",
                username != null ? username : "").trim();
    }


    public Integer getMaxAssignments() {
        return maxAssignments;
    }

    public void setMaxAssignments(Integer maxAssignments) {
        this.maxAssignments = maxAssignments;
    }

    public ContextRole getContextRole() {
        return contextRole == null ? null : ContextRole.fromId(contextRole);
    }

    public void setContextRole(ContextRole contextRole) {
        this.contextRole = contextRole == null ? null : contextRole.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}