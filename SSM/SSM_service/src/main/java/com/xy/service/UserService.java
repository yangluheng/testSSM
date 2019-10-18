package com.xy.service;

import com.xy.domain.Role;
import com.xy.domain.UserInfo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * 用户业务层接口
 */
public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    /**
     * 查询所有用户
     * @return
     */
    public List<UserInfo> findAll();

    /**
     * 添加用户
     * @param userInfo
     */
    public void save(UserInfo userInfo);

    /**
     * 通过ID查询用户
     * @param id
     * @return
     */
    public UserInfo findById(String id);

    /**
     * 根据用户ID查询其它角色
     * @param userId
     * @return
     */
    public List<Role> findOtherRoles(String userId);

    /**
     * 用户添加角色
     * @param userId
     * @param roleIds
     */
    public void addRoleToUser(String userId,String []roleIds);
}
