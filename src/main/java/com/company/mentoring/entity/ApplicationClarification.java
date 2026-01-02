package com.company.mentoring.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "APPLICATION_CLARIFICATION", indexes = {
        @Index(name = "IDX_APPLICATION_CLARIFICATION_APPLICATION", columnList = "APPLICATION_ID"),
        @Index(name = "IDX_APPLICATION_CLARIFICATION_AUTHOR", columnList = "AUTHOR_ID")
})
@Entity
public class ApplicationClarification {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "APPLICATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    @JoinColumn(name = "AUTHOR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceParticipant author;

    @Column(name = "MESSAGE")
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WorkspaceParticipant getAuthor() {
        return author;
    }

    public void setAuthor(WorkspaceParticipant author) {
        this.author = author;
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