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
        @Index(name = "IDX_TEAM_MEMBER_PARTICIPANT", columnList = "PARTICIPANT_ID")
})
@Entity
public class TeamMember {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @JoinColumn(name = "PARTICIPANT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceParticipant participant;

    public WorkspaceParticipant getParticipant() {
        return participant;
    }

    public void setParticipant(WorkspaceParticipant participant) {
        this.participant = participant;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}