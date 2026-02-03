package com.company.mentoring.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "WORKSPACE", indexes = {
        @Index(name = "IDX_WORKSPACE_OWNER", columnList = "OWNER_ID")
})
@Entity
public class Workspace {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "STATUS")
    private String status;

    @JoinColumn(name = "OWNER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(name = "MAX_MEMBERS_IN_TEAM")
    private Integer maxMembersInTeam;

    @Composition
    @OneToMany(mappedBy = "workspace")
    private List<WorkspaceParticipant> participants;

    public Integer getMaxMembersInTeam() {
        return maxMembersInTeam;
    }

    public void setMaxMembersInTeam(Integer maxMembersInTeam) {
        this.maxMembersInTeam = maxMembersInTeam;
    }

    public List<WorkspaceParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<WorkspaceParticipant> participants) {
        this.participants = participants;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public WorkspaceStatus getStatus() {
        return status == null ? null : WorkspaceStatus.fromId(status);
    }

    public void setStatus(WorkspaceStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}