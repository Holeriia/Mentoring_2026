package com.company.mentoring.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "GLOBAL_TEAM_MEMBER", indexes = {
        @Index(name = "IDX_GLOBAL_TEAM_MEMBER_GLOBAL_TEAM", columnList = "GLOBAL_TEAM_ID"),
        @Index(name = "IDX_GLOBAL_TEAM_MEMBER_USER", columnList = "USER_ID")
})
@Entity
public class GlobalTeamMember {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @JoinColumn(name = "GLOBAL_TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private GlobalTeam globalTeam;

    @NotNull
    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GlobalTeam getGlobalTeam() {
        return globalTeam;
    }

    public void setGlobalTeam(GlobalTeam globalTeam) {
        this.globalTeam = globalTeam;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}