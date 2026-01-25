package com.company.mentoring.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "APPLICATION", indexes = {
        @Index(name = "IDX_APPLICATION_WORKSPACE", columnList = "WORKSPACE_ID"),
        @Index(name = "IDX_APPLICATION_CURRENT_RECIPIENT", columnList = "CURRENT_RECIPIENT_ID")
})
@Entity
public class Application {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "WORKSPACE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    @JoinColumn(name = "INITIATOR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceParticipant initiator;

    @JoinColumn(name = "CURRENT_RECIPIENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceParticipant currentRecipient;

    @InstanceName
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CURRENT_PRIORITY_INDEX")
    private Integer currentPriorityIndex;

    @Column(name = "PROCESS_INSTANCE_ID")
    private String processInstanceId;

    @Composition
    @OneToMany(mappedBy = "application")
    private List<ApplicationPriority> priorities;

    @Composition
    @OneToMany(mappedBy = "application")
    private List<ApplicationClarification> clarifications;

    public List<ApplicationClarification> getClarifications() {
        return clarifications;
    }

    public void setClarifications(List<ApplicationClarification> clarifications) {
        this.clarifications = clarifications;
    }

    public List<ApplicationPriority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<ApplicationPriority> priorities) {
        this.priorities = priorities;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getCurrentPriorityIndex() {
        return currentPriorityIndex;
    }

    public void setCurrentPriorityIndex(Integer currentPriorityIndex) {
        this.currentPriorityIndex = currentPriorityIndex;
    }

    public ApplicationStatus getStatus() {
        return status == null ? null : ApplicationStatus.fromId(status);
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status == null ? null : status.getId();
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

    public WorkspaceParticipant getCurrentRecipient() {
        return currentRecipient;
    }

    public void setCurrentRecipient(WorkspaceParticipant currentRecipient) {
        this.currentRecipient = currentRecipient;
    }

    public WorkspaceParticipant getInitiator() {
        return initiator;
    }

    public void setInitiator(WorkspaceParticipant initiator) {
        this.initiator = initiator;
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