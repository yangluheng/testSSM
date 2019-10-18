package com.xy.service;

import com.xy.domain.Permission;
import com.xy.domain.Role;

import java.util.List;

/**
 * 角色业务层接口
 */
public interface RoleService {
    /**
     * 根据用户ID查询所有角色
     * @param userId
     * @return
     */
    public List<Role> findRoleByUserId(String userId);

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> findAll();

    /**
     * 角色添加
     * @param role
     */
    public void save(Role role);

    /**
     * 根据角色ID查询
     * @param roleId
     * @return
     */
    public Role findById(String roleId);

    /**
     * 根据角色ID查询可以添加的权限
     * @param roleId
     * @return
     */
    public List<Permission> findOtherPermissions(String roleId);

    /**
     * 添加角色权限
     * @param roleId
     * @param permissionId
     */
    public void addPermissionToRole(String roleId,String []permissionId);
}
