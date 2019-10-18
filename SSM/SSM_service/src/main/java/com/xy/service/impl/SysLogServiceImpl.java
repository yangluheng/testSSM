package com.xy.service.impl;

import com.xy.dao.SysLogDao;
import com.xy.domain.SysLog;
import com.xy.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;
    /**
     * 日志保存
     *
     * @param sysLog
     */
    @Override
    public void save(SysLog sysLog) {
        sysLogDao.save(sysLog);
    }

    /**
     * 查询日志
     *
     * @return
     */
    @Override
    public List<SysLog> findAll() {
        return sysLogDao.findAll();
    }
}
