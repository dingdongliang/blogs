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
     * 获取所有的角色列表，用于分页展示角色
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:08
     */
    @RequiresPermissions("role:list")
    @GetMapping("/roleList")
    public ResponseData roleList(@ApiParam(name = "requestJson", value = "格式为{\"pageNo\":\"1\"}", required = true)
                                 @RequestBody JSONObject requestJson) throws GlobalException {
        JsonUtil.hasAllRequired(requestJson, "pageNo");
        int pageNo = Integer.parseInt(requestJson.getString("pageNo"));
        PageInfo<SysRole> sysRolePageInfo = sysRoleService.selectPageByAll(pageNo, SystemConstant.PAGE_SIZE);
        return ResponseData.success(sysRolePageInfo);
    }

    /**
     * 新增角色
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:09
     */

    @PostMapping("/addRole")
    public ResponseData addRole(SysRole sysRole) throws GlobalException {

        String roleId = StringUtil.getUUID();

        sysRole.setRoleId(roleId);

        BaseDomain.createLog(sysRole);

        sysRoleService.insert(sysRole);

        return ResponseData.success();
    }

    /**
     * 修改角色，状态全部为E
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:09
     */
    @RequiresPermissions("role:update")
    @PostMapping("/updateRole")
    public ResponseData updateRole(@ApiParam(name = "requestJson", value = "格式为{\"roleId\":" +
            "\"8cefc3f9409348bb9677118aed62fdfb\",\"roleName\":\"破坏者\",\"permissions\":" +
            "\"2e0b4be914de494d99236f7d5141804a|6b12817ab5b943e1b4d4218617dd3ca3\"}",
            required = true) @RequestBody JSONObject requestJson) throws GlobalException {
        JsonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");

        String roleId = requestJson.getString("roleId");
        String roleName = requestJson.getString("roleName");
        String pmsnStr = requestJson.getString("permissions");
        String[] pmsnIds = pmsnStr.split("\\|");

        SysRole sysRole = sysRoleService.selectByPrimaryKey(roleId);
        sysRole.setRoleName(roleName);

        sysRoleService.update(sysRole, pmsnIds);

        return ResponseData.success();

    }

    /**
     * 删除角色
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:09
     */
    @RequiresPermissions("role:delete")
    @DeleteMapping("/deleteRole")
    public ResponseData deleteRole(@RequestBody JSONObject requestJson) throws GlobalException {
        JsonUtil.hasAllRequired(requestJson, "roleId");
        String roleId = requestJson.getString("roleId");
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

