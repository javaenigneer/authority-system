package com.feicheng.authority.monitor.service.impl;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.monitor.entity.ActiveUser;
import com.feicheng.authority.monitor.service.SessionService;
import com.feicheng.authority.system.entity.Admin;
import com.feicheng.authority.utils.AddressUtil;
import com.feicheng.authority.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Session Service
 *
 * @author Lenovo
 */
@Service
public class SessionServiceImpl implements SessionService {


    @Autowired(required = false)
    private SessionDAO sessionDAO;


    /**
     * 查询全部的在线用户
     *
     * @param userName
     * @return
     */
    @Override
    public ResponseResult<ActiveUser> list(String userName) {

        String currentSessionId = (String) SecurityUtils.getSubject().getSession().getId();

        List<ActiveUser> list = new ArrayList<>();

        Collection<Session> sessions = this.sessionDAO.getActiveSessions();

        for (Session session : sessions
        ) {
            ActiveUser activeUser = new ActiveUser();

            Admin admin;

            SimplePrincipalCollection simplePrincipalCollection;

            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {

                continue;
            } else {

                simplePrincipalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

                admin = (Admin) simplePrincipalCollection.getPrimaryPrincipal();

                activeUser.setUserName(admin.getAdminName());

                activeUser.setUserId(admin.getAdminId().toString());
            }

            activeUser.setId((String) session.getId());

            activeUser.setHost(session.getHost());

            activeUser.setStartTimestamp(DateUtils.formatDate(session.getStartTimestamp()));

            activeUser.setLastAccessTime(DateUtils.formatDate(session.getLastAccessTime()));

            Long timeout = session.getTimeout();

            activeUser.setStatus(timeout == 0L ? "0" : "1");

            String address = AddressUtil.getCityInfo(activeUser.getHost());

            activeUser.setLocation(address);

            activeUser.setTimeOut(timeout);

            if (StringUtils.equals(currentSessionId, activeUser.getId())) {
                activeUser.setCurrent(true);
            }
            if (StringUtils.isBlank(userName)
                    || StringUtils.equalsIgnoreCase(activeUser.getUserName(), userName)) {
                list.add(activeUser);
            }
        }

        return new ResponseResult<>(200, "查询成功", list, (long) list.size());
    }
}
