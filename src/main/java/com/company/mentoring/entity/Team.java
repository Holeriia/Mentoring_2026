package com.company.mentoring.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@JmixEntity
@Table(name = "TEAM", indexes = {
        @Index(name = "IDX_TEAM_APPLICATION", columnList = "APPLICATION_ID")
})
@Entity
public class Team {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "APPLICATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    @Column(name = "TEAM_TYPE")
    private String teamType;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "team")
    private List<TeamMember> members;

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

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public TeamType getTeamType() {
        return teamType == null ? null : TeamType.fromId(teamType);
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType == null ? null : teamType.getId();
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