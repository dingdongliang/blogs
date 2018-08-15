package net.htjs.blog.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * blog/net.htjs.blog.shiro
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:33
 */
public class AuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            WebUtils.toHttp(response).sendError(403);
        }
        return false;
    }
}

