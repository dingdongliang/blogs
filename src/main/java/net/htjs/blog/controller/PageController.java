package net.htjs.blog.controller;

import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.entity.*;
import net.htjs.blog.service.*;
import net.htjs.blog.util.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * blog/net.htjs.blog.controller
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/22 11:54
 */
@Controller
@Slf4j
public class PageController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private BlogArticleService blogArticleService;
    @Resource
    private SysPermissionService sysPermissionService;
    @Resource
    private BlogSortService blogSortService;

    /**
     * 前台展示分类页面（按年份分组）
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:52
     */
    @GetMapping("/sort")
    public String sort() {
        return "front/sort";
    }

    /**
     * 后台登陆界面
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:52
     */
    @GetMapping("/logon")
    public String login() {
        return "login";
    }

    /**
     * 后台管理大屏
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:53
     */
    @GetMapping("/manage/main")
    public String main(HttpSession session) {

        List<SysPermission> sysPermissionList = sysPermissionService.getUserMenu(ShiroUtil.getUser().getUserId());

        session.setAttribute("sysPermissionList", sysPermissionList);
        return "backend/main";
    }

    /**
     * 后台用户管理界面
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:53
     */
    @RequestMapping("/manage/user")
    public String user(Model model) {
        List<SysUser> sysUserList = sysUserService.selectAll();
        List<SysRole> sysRoleList = sysRoleService.selectAll();

        model.addAttribute("sysUserList", sysUserList);
        model.addAttribute("sysRoleList", sysRoleList);

        return "backend/userList";
    }

    /**
     * 后台角色管理界面，或者在页面加载的时候，调用获取角色分页接口实现
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:53
     */
    @GetMapping("/manage/role")
    public String role(Model model) {
        List<SysRole> sysRoleList = sysRoleService.selectAll();
        model.addAttribute("sysRoleList", sysRoleList);
        return "backend/roleList";
    }

    /**
     * 后台权限配置界面
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:53
     */
    @GetMapping("/manage/pmsn")
    public String pmsn() {
        return "backend/pmsnList";
    }

    /**
     * 用户资料页面
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:59
     */
    @GetMapping("/manage/system")
    public String sysInfo() {
        return "backend/system";
    }


    @GetMapping("/manage/article")
    public String article(Model model) {
        List<BlogArticle> blogArticleList = blogArticleService.selectAll();

        model.addAttribute("blogArticleList", blogArticleList);
        return "backend/articleList";
    }

    /**
     * 文章编辑跳转，注意mapping中value的写法和pathvariable的写法
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/30 14:42
     */
    @GetMapping(value = {"/manage/articleEdit/{articleId}", "/manage/articleEdit"})
    public String articleEdit(@PathVariable(required = false) String articleId, Model model) {
        if (!StringUtils.isBlank(articleId)) {
            BlogArticle blogArticle = blogArticleService.selectByPrimaryKey(articleId);
            model.addAttribute("blogArticle", blogArticle);
        }

        List<BlogSort> blogSortList = blogSortService.selectAll();
        model.addAttribute("blogSortList", blogSortList);

        return "backend/articleEdit";
    }
}
