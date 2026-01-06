package com.company.mentoring.security;

import com.company.mentoring.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "MemberRole", code = MemberRole.CODE)
public interface MemberRole {
    String CODE = "member-role";

    @EntityAttributePolicy(entityClass = Application.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Application.class, actions = EntityPolicyAction.ALL)
    void application();

    @EntityAttributePolicy(entityClass = Team.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Team.class, actions = EntityPolicyAction.ALL)
    void team();

    @EntityAttributePolicy(entityClass = TeamMember.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = TeamMember.class, actions = EntityPolicyAction.ALL)
    void teamMember();

    @EntityPolicy(entityClass = Workspace.class, actions = EntityPolicyAction.READ)
    void workspace();

    @EntityAttributePolicy(entityClass = ApplicationClarification.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ApplicationClarification.class, actions = EntityPolicyAction.ALL)
    void applicationClarification();

    @EntityAttributePolicy(entityClass = ApplicationPriority.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ApplicationPriority.class, actions = EntityPolicyAction.ALL)
    void applicationPriority();

    @EntityAttributePolicy(entityClass = Assignment.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Assignment.class, actions = EntityPolicyAction.ALL)
    void assignment();

    @EntityPolicy(entityClass = WorkspaceParticipant.class, actions = EntityPolicyAction.READ)
    void workspaceParticipant();

    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @MenuPolicy(menuIds = {"WorkspaceDashboardView", "Workspace.list", "User.list"})
    @ViewPolicy(viewIds = {"WorkspaceDashboardView", "Workspace.list", "User.list", "Workspace.detail", "Application.approval", "Application.detail", "Assignment.detail", "Assignment.list"})
    void screens();
}