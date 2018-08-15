package net.htjs.blog.shiro;

import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.constant.SystemConstant;
import net.htjs.blog.entity.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * blog/net.htjs.blog.shiro
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:33
 */
@Slf4j
public class KickoutSessionControlFilter extends AccessControlFilter {

    private boolean kickoutAfter = false;

    private int maxSession = 1;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public int getMaxSession() {
        return maxSession;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws
            Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);

        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }

        Session session = subject.getSession();
        String username = ((SysUser) subject.getPrincipal()).getAccount();
        Serializable sessionId = session.getId();

        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            deque = new LinkedList<>();
            cache.put(username, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute(SystemConstant.KICKOUT) == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人,kickoutAfter判断是提出前者还是后者
        while (deque.size() > SystemConstant.ACCOUNT_MAX_SESSION) {
            Serializable kickoutSessionId;
            if (kickoutAfter) {
                kickoutSessionId = deque.removeFirst();
            } else {
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute(SystemConstant.KICKOUT, true);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        if (session.getAttribute(SystemConstant.KICKOUT) != null) {

            try {
                //如果被踢出了，直接退出
                subject.logout();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            saveRequest(request);
            WebUtils.toHttp(response).sendError(401);

            return false;
        }

        return true;
    }
}
