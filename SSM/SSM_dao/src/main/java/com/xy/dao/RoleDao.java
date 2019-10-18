package com.xy.dao;

import com.xy.domain.Permission;
import com.xy.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 角色DAO
 */
public interface RoleDao {
    /**
     * 根据用户ID查询角色
     * @param userId
     * @return
     */
    //先从关联表里查询用户ID，再根据对应ID查询对应的角色
    @Select("select *from role where id in(select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.xy.dao.PermissionDao.findPermissionByRoleId"))
    })
    public List<Role> findRoleByUserId(String userId);

    /**
     * 查询所有角色
     * @return
     */
    @Select("select *from role")
    public List<Role> findAll();

    /**
     * 角色添加
     * @param role
     */
    @Select("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    public void save(Role role);

    /**
     * 根据角色ID查询
     * @param roleId
     * @return
     */
    @Select("select *from role where id =#{roleId}")
    public Role findById(String roleId);

    /**
     * 根据根据角色ID查询可以添加的权限
     * @param roleId
     * @return
     */
    @Select("select *from permission where id not in(select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findOtherPermissions(String roleId);

    /**
     * 添加角色权限
     * @param roleId
     * @param permissionId
     */
    @Select("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    public void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId);
}
