package com.company.mentoring.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "ASSIGNMENT", indexes = {
        @Index(name = "IDX_ASSIGNMENT_WORKSPACE", columnList = "WORKSPACE_ID"),
        @Index(name = "IDX_ASSIGNMENT_EXECUTORS_TEAM", columnList = "EXECUTORS_TEAM_ID"),
        @Index(name = "IDX_ASSIGNMENT_SUPERVISORS_TEAM", columnList = "SUPERVISORS_TEAM_ID"),
        @Index(name = "IDX_ASSIGNMENT_APPLICATION", columnList = "APPLICATION_ID")
})
@Entity
public class Assignment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "APPLICATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    @InstanceName
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @JoinColumn(name = "EXECUTORS_TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team executorsTeam;

    @JoinColumn(name = "SUPERVISORS_TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team supervisorsTeam;

    @JoinColumn(name = "WORKSPACE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    @Column(name = "STATUS")
    private String status;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Team getSupervisorsTeam() {
        return supervisorsTeam;
    }

    public void setSupervisorsTeam(Team supervisorsTeam) {
        this.supervisorsTeam = supervisorsTeam;
    }

    public Team getExecutorsTeam() {
        return executorsTeam;
    }

    public void setExecutorsTeam(Team executorsTeam) {
        this.executorsTeam = executorsTeam;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AssignmentStatus getStatus() {
        return status == null ? null : AssignmentStatus.fromId(status);
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status == null ? null : status.getId();
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