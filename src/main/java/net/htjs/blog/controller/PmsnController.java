package net.htjs.blog.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.constant.SystemConstant;
import net.htjs.blog.entity.BaseDomain;
import net.htjs.blog.entity.SysPermission;
import net.htjs.blog.exception.GlobalException;
import net.htjs.blog.exception.ResponseData;
import net.htjs.blog.constant.TreeModel;
import net.htjs.blog.service.SysPermissionService;
import net.htjs.blog.util.JsonUtil;
import net.htjs.blog.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * blog/net.htjs.blog.controller
 *
 * @Description: 权限操作
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:20
 */
@RestController
@RequestMapping("/pmsn")
@Api(value = "航天金穗研发部技术拾遗-权限相关操作接口")
@Slf4j
public class PmsnController {

    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 获取所有的权限列表，使用树状展示，还可在角色分配权限时调用
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:08
     */

    @GetMapping("/getAllPmsn")
    public ResponseData getAllPmsn() {
        List<SysPermission> sysPermissionList = sysPermissionService.selectAll();
        return ResponseData.success(sysPermissionList);
    }

    @GetMapping("/setPmsn")
    public List<TreeModel> setPmsn() {
        List<SysPermission> sysPermissionList = sysPermissionService.selectAll();
        return pmsnToTree("0", sysPermissionList);
    }

    /**
     * 递归转化成Tree模型，支持无限级节点
     *
     * @param id
     * @param list
     * @return
     */
    private List<TreeModel> pmsnToTree(String id, List<SysPermission> list) {
        List<TreeModel> menuList = new ArrayList<>();
        list.stream().filter(co -> id.equals(co.getPrntId())).forEach(co -> {
            TreeModel menu = new TreeModel();
            menu.setId(co.getPmsnId());
            menu.setPid(StringUtils.isBlank(co.getPrntId()) ? "0" : co.getPrntId());

            menu.setText(co.getMenuName());
            menu.setNodes(pmsnToTree(co.getPmsnId(), list));
            menuList.add(menu);
        });
        return menuList;
    }

    /**
     * 新增或修改权限,//TODO 如果是通用权限，还需要添加所有的角色权限映射
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:09
     */
    @PostMapping("/saveOrUpdatePmsn")
    public ResponseData saveOrUpdatePmsn(SysPermission sysPermission) {
        sysPermissionService.persistencePmsn(sysPermission);
        return ResponseData.success();
    }


    /**
     * 删除权限，需要同时删除角色-权限对应表中的该权限记录
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:09
     */
    @PostMapping("/deletePmsn")
    public ResponseData deletePmsn(@RequestParam("pmsnId") String pmsnId) {
        sysPermissionService.delete(pmsnId);
        return ResponseData.success();
    }
}

