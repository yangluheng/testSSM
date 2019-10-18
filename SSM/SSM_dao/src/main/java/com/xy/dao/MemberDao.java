package com.xy.dao;

import com.xy.domain.Member;
import org.apache.ibatis.annotations.Select;

/**
 * 会员DAO接口
 */
public interface MemberDao {



    @Select("select *from member where id=#{id}")
    /**
     * 根据ID查询会员
     * @param memberId
     * @return
     */
    public Member findById(String memberId);
}
