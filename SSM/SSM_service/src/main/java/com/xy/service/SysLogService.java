package com.xy.service;

import com.xy.domain.SysLog;

import java.util.List;

/**
 * 日志业务层接口
 */
public interface SysLogService {
    /**
     * 日志保存
     * @param sysLog
     */
    public void save(SysLog sysLog);

    /**
     * 查询日志
     * @return
     */
    public List<SysLog> findAll();
}
