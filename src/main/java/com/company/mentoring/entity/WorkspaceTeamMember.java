package com.company.mentoring.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "TEAM_MEMBER", indexes = {
        @Index(name = "IDX_TEAM_MEMBER_TEAM", columnList = "TEAM_ID"),
        @Index(name = "IDX_TEAM_MEMBER_PARTICIPANT", columnList = "PARTICIPANT_ID"),
        @Index(name = "IDX_TEAM_MEMBER_LOCAL_ROLE", columnList = "LOCAL_ROLE_ID")
})
@Entity
public class WorkspaceTeamMember {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "PARTICIPANT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceParticipant participant;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceTeam workspaceTeam;

    @JoinColumn(name = "LOCAL_ROLE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private LocalRole localRole;

    public LocalRole getLocalRole() {
        return localRole;
    }

    public void setLocalRole(LocalRole localRole) {
        this.localRole = localRole;
    }

    public WorkspaceParticipant getParticipant() {
        return participant;
    }

    public void setParticipant(WorkspaceParticipant participant) {
        this.participant = participant;
    }

    public WorkspaceTeam getTeam() {
        return workspaceTeam;
    }

    public void setTeam(WorkspaceTeam workspaceTeam) {
        this.workspaceTeam = workspaceTeam;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}