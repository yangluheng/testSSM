package com.xy.service.impl;

import com.xy.dao.RoleDao;
import com.xy.domain.Permission;
import com.xy.domain.Role;
import com.xy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色业务层接口
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;
    /**
     * 根据用户ID查询所有角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findRoleByUserId(String userId) {
        return null;
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /**
     * 角色添加
     *
     * @param role
     */
    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    /**
     * 根据角色ID查询
     *
     * @param roleId
     * @return
     */
    @Override
    public Role findById(String roleId) {
        return roleDao.findById(roleId);
    }

    /**
     * 根据角色ID查询可以添加的权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> findOtherPermissions(String roleId) {
        return roleDao.findOtherPermissions(roleId);
    }

    /**
     * 添加角色权限
     *
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void addPermissionToRole(String roleId, String[] permissionIds) {
        for (String permissionId:permissionIds
             ) {
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }
}
