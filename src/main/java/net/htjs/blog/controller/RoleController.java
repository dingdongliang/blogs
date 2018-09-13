package net.htjs.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.constant.SystemConstant;
import net.htjs.blog.entity.BaseDomain;
import net.htjs.blog.entity.SysRole;
import net.htjs.blog.exception.GlobalException;
import net.htjs.blog.exception.ResponseData;
import net.htjs.blog.service.SysPermissionService;
import net.htjs.blog.service.SysRoleService;
import net.htjs.blog.util.JsonUtil;
import net.htjs.blog.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * blog/net.htjs.blog.controller
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:38
 */
@RestController
@RequestMapping("/role")
@Api(value = "航天金穗研发部技术拾遗-角色相关操作接口")
@Slf4j
public class RoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 新增或修改角色
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:09
     */
    @PostMapping("/saveOrUpdateRole")
    public ResponseData saveOrUpdateRole(SysRole sysRole) {

        sysRoleService.persistenceRole(sysRole);
        return ResponseData.success();
    }


    /**
     * 删除角色
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:09
     */
    @PostMapping("/deleteRole")
    public ResponseData deleteRole(@RequestParam("roleId") String roleId) {
        sysRoleService.delete(roleId);
        return ResponseData.success();
    }


    /**
     * 保存某个角色的权限分配
     * param request
     * return
     */
    @PostMapping("/savePermission")
    public ResponseData savePermission(@RequestParam("roleId") String roleId, @RequestParam("pmsnIds") String pmsnIds) {
        sysRoleService.savePermission(roleId, pmsnIds);
        return ResponseData.success();
    }
}

