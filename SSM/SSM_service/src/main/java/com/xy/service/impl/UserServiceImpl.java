package com.xy.service.impl;

import com.xy.dao.UserDao;
import com.xy.domain.Role;
import com.xy.domain.UserInfo;
import com.xy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
//    @Autowired     //注入bean，在spring security.xml中要有对应的
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo=null;
        try{
            userInfo=userDao.findByUserName(username);
        }catch (Exception e){
            e.printStackTrace();
        }
//        User user=new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(), getAuthority(userInfo.getRoles()));
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        User user=new User(userInfo.getUsername(),"{noop}"+bCryptPasswordEncoder.encode(userInfo.getPassword()),userInfo.getStatus()==0?false:true,true,true,true,getAuthority(userInfo.getRoles()));
        return user;
    }
    /**
     * 作用就是返回一个List集合，集合中装入的是角色描述
     * @return
     * @param roles
     */
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list=new ArrayList<>();
        for (Role role:roles
             ) {
            list.add(new SimpleGrantedAuthority("ROLE_USER"+role.getRoleName()));
        }
        return list;
    }
    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<UserInfo> findAll() {
        return userDao.findAll();
    }
    /**
     * 添加用户
     *对密码机型==进行加密
     * @param userInfo
     */
    @Override
    public void save(UserInfo userInfo) {
        //对密码进行加密
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        bCryptPasswordEncoder.encode(userInfo.getPassword());
        userDao.save(userInfo);
    }

    /**
     * 通过ID查询用户
     *
     * @param id
     * @return
     */
    @Override
    public UserInfo findById(String id) {
        return userDao.findById(id);
    }

    /**
     * 根据用户ID查询其它角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findOtherRoles(String userId) {
        return userDao.findOtherRoles(userId);
    }

    /**
     * 用户添加角色
     *
     * @param userId
     * @param roleIds
     */
    @Override
    public void addRoleToUser(String userId, String[] roleIds) {
        for (String roleId:roleIds
             ) {
            userDao.addRoleToUser(userId,roleId);
        }
    }


}
