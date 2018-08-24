package net.htjs.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.constant.RespCodeEnum;
import net.htjs.blog.entity.SysPermission;
import net.htjs.blog.entity.SysUser;
import net.htjs.blog.exception.ApiException;
import net.htjs.blog.exception.GlobalException;
import net.htjs.blog.exception.ResponseData;
import net.htjs.blog.service.SysPermissionService;
import net.htjs.blog.service.SysUserService;
import net.htjs.blog.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * blog/net.htjs.blog.controller
 *
 * @Description: 登录操作
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:10
 */
@RestController
@Api(value = "航天金穗大数据组技术博客-用户登录接口（登录验证和退出）")
@Slf4j
public class LoginController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 登录验证
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 14:22
     */
    @ApiOperation(value = "用户登陆方法", notes = "详细说明文档")
    @PostMapping("/login")
    public ResponseData authLogin(HttpServletRequest request) throws GlobalException {

        String account = request.getParameter("account");
        String userPwd = request.getParameter("userPwd");

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, userPwd);

        try {

            if (!currentUser.isAuthenticated()) {
                token.setRememberMe(false);
                currentUser.login(token);
            }
            log.info("对用户[{}]进行登录验证..验证通过", account);

        } catch (UnknownAccountException e) {
            log.error(RespCodeEnum.NO_ACCOUNT.getMessage());
            throw new ApiException(RespCodeEnum.NO_ACCOUNT);
        } catch (IncorrectCredentialsException e) {
            log.error(RespCodeEnum.PWD_ERROR + e.getMessage());
            throw new ApiException(RespCodeEnum.PWD_ERROR);
        } catch (LockedAccountException e) {
            log.error(RespCodeEnum.ACCOUNT_LOCKED.getMessage());
            throw new ApiException(RespCodeEnum.ACCOUNT_LOCKED);
        } catch (ExcessiveAttemptsException e) {
            log.error(RespCodeEnum.TRY_MORE_FIVE.getMessage());
            throw new ApiException(RespCodeEnum.TRY_MORE_FIVE);
        } catch (DisabledAccountException e) {
            log.error(RespCodeEnum.ACCOUNT_FORBID.getMessage());
            throw new ApiException(RespCodeEnum.ACCOUNT_FORBID);
        } catch (AuthenticationException e) {
            log.error(RespCodeEnum.STH_ERROR.getMessage());
            throw new ApiException(RespCodeEnum.STH_ERROR);
        }

        return ResponseData.success(setUserPmsn((SysUser) currentUser.getPrincipal()));
    }

    /**
     * 登出，会触发退出事件，清空用户状态，见ShiroRealm中的onLogout方法
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 14:23
     */
    @PostMapping("/logout")
    public ResponseData logout() throws GlobalException {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(RespCodeEnum.SERVER_ERROR);
        }
        return ResponseData.success();
    }

    /**
     * 获取当前用户的权限
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/23 17:51
     */
    @GetMapping("/getCurrentPmsn")
    public ResponseData getCurrentPmsn() {

        String userId = ShiroUtil.getUserId();

        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
        sysUser.setUserPwd("");

        return ResponseData.success(setUserPmsn(sysUser));
    }

    /**
     * 根据用户对象获取其权限
     *
     * @param sysUser 用户对象
     * @return net.htjs.blog.entity.SysUser
     * @author dingdongliang
     * @date 2018/4/27 16:23
     */
    private SysUser setUserPmsn(SysUser sysUser) {
        List<SysPermission> sysPermissionList = sysPermissionService.getCurrentPmsn(sysUser);

        Set<String> menuList = new HashSet<>();
        Set<String> pmsnList = new HashSet<>();

        for (SysPermission sysPermission : sysPermissionList) {
            menuList.add(sysPermission.getMenuCode());
            pmsnList.add(sysPermission.getPmsnCode());
        }

        sysUser.setMenuList(menuList);
        sysUser.setPmsnList(pmsnList);
        return sysUser;
    }
}


