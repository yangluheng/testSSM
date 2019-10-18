package com.xy.dao;

import com.xy.domain.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PermissionDao {
    /**
     * 查询权限
     * @return
     */
    //先查询出角色ID，在查询对应的权限信息
    @Select("select *from permission where id in(select permissionId from role_permission where roleId=#{id})")
    public List<Permission> findPermissionByRoleId();

    /**
     * 查询所有权限
     * @return
     */
    @Select("select *from permission")
    public List<Permission> findAll();

    /**
     * 资源权限的添加
     * @param permission
     */
    @Select("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    public void save(Permission permission);
}
