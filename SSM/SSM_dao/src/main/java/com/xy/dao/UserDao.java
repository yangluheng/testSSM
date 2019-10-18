package com.xy.dao;

import com.xy.domain.Role;
import com.xy.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.userdetails.User;

import javax.sql.rowset.serial.SerialStruct;
import java.util.List;

/**
 * 用户DAO
 */
public interface UserDao {

    @Select("select *from users where username=#{username}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.xy.dao.RoleDao.findRoleByUserId"))
    }
    )
    /**
     * 通过用户名查询
     * @param username
     * @return
     */
    public UserInfo findByUserName(String username);

    @Select("select *from users where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.xy.dao.RoleDao.findRoleByUserId"))
    })
    /**
     * 通过用户ID查询
     * @param id
     * @return
     */
    public UserInfo findById(String id);

    /**
     * 查询所有用户
     * @return
     */
    @Select("select *from users")
    public List<UserInfo> findAll();

    /**
     * 添加用户信息
     * 要注意将密码加密输入
     * @param userInfo
     */
    @Select("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    public void save(UserInfo userInfo);

    /**
     * 根据用户ID查询其它角色
     * @param userId
     * @return
     */
    @Select("select *from role where id not in(select roleId from users_role where userId=#{userId})")
    public List<Role> findOtherRoles(String userId);

    /**
     * 用户添加角色
     * @param userId
     * @param roleId
     */
    @Select("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    //public void addRoleToUser(String userId,String roleId);//注意这种写法会让mybatis认为是两个对象的两个属性
    public void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);
}
