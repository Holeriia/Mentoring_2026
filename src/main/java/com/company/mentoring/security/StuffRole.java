package com.company.mentoring.security;

import com.company.mentoring.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "StuffRole", code = StuffRole.CODE)
public interface StuffRole {
    String CODE = "stuff-role";

    @MenuPolicy(menuIds = "WorkspaceDashboardView")
    @ViewPolicy(viewIds = {"Assignment.creating", "WorkspaceDashboardView"})
    void screens();

    @EntityAttributePolicy(entityClass = Assignment.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Assignment.class, actions = EntityPolicyAction.ALL)
    void assignment();

    @EntityAttributePolicy(entityClass = Team.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Team.class, actions = EntityPolicyAction.ALL)
    void team();

    @EntityAttributePolicy(entityClass = TeamMember.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = TeamMember.class, actions = EntityPolicyAction.ALL)
    void teamMember();

    @EntityAttributePolicy(entityClass = Workspace.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Workspace.class, actions = EntityPolicyAction.ALL)
    void workspace();

    @EntityAttributePolicy(entityClass = WorkspaceParticipant.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = WorkspaceParticipant.class, actions = EntityPolicyAction.ALL)
    void workspaceParticipant();

    @EntityAttributePolicy(entityClass = Application.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Application.class, actions = EntityPolicyAction.ALL)
    void application();

    @EntityAttributePolicy(entityClass = ApplicationClarification.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ApplicationClarification.class, actions = EntityPolicyAction.ALL)
    void applicationClarification();

    @EntityAttributePolicy(entityClass = ApplicationPriority.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ApplicationPriority.class, actions = EntityPolicyAction.ALL)
    void applicationPriority();

    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();
}