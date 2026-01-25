package com.company.mentoring.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "TEAM", indexes = {
        @Index(name = "IDX_TEAM_WORKSPACE", columnList = "WORKSPACE_ID"),
        @Index(name = "IDX_TEAM_GLOBAL_TEAM", columnList = "GLOBAL_TEAM_ID")
})
@Entity
public class WorkspaceTeam {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TEAM_TYPE")
    private String teamType;

    @JoinColumn(name = "WORKSPACE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    @JoinColumn(name = "GLOBAL_TEAM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private GlobalTeam globalTeam;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "workspaceTeam")
    private List<WorkspaceTeamMember> members;

    public GlobalTeam getGlobalTeam() {
        return globalTeam;
    }

    public void setGlobalTeam(GlobalTeam globalTeam) {
        this.globalTeam = globalTeam;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @InstanceName
    @DependsOnProperties({"members"})
    public String getInstanceName() {
        if (members == null || members.isEmpty()) return "";

        return members.stream()
                // безопасно получаем participant
                .map(m -> {
                    try {
                        return m.getParticipant() != null ? m.getParticipant().getInstanceName() : "";
                    } catch (Exception e) {
                        return ""; // если participant не загружен, возвращаем пустую строку
                    }
                })
                .filter(s -> !s.isBlank())
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse("");
    }

    public List<WorkspaceTeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<WorkspaceTeamMember> members) {
        this.members = members;
    }

    public TeamType getTeamType() {
        return teamType == null ? null : TeamType.fromId(teamType);
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType == null ? null : teamType.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}