package com.xy.service;

import com.xy.domain.Permission;

import java.util.List;

/**
 * 权限业务层接口
 */
public interface PermissionService {
    /**
     * 查询所有权限
     * @return
     */
    public List<Permission> findAll();

    /**
     * 添加权限
     * @param permission
     */
    public void save(Permission permission);
}
