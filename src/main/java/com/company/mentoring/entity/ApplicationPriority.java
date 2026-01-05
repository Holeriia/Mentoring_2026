package com.company.mentoring.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "APPLICATION_PRIORITY", indexes = {
        @Index(name = "IDX_APPLICATION_PRIORITY_APPLICATION", columnList = "APPLICATION_ID"),
        @Index(name = "IDX_APPLICATION_PRIORITY_SUPERVISOR", columnList = "SUPERVISOR_ID")
})
@Entity
public class ApplicationPriority {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "APPLICATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    @JoinColumn(name = "PARTICIPANT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceParticipant participant;

    @Column(name = "PRIORITY_NUMBER")
    private Integer priorityNumber;

    public Integer getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(Integer priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public WorkspaceParticipant getParticipant() {
        return participant;
    }

    public void setParticipant(WorkspaceParticipant participant) {
        this.participant = participant;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}