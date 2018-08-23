package net.htjs.blog.controller;

import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.entity.BlogArticle;
import net.htjs.blog.entity.SysUser;
import net.htjs.blog.service.BlogArticleService;
import net.htjs.blog.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
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
    private BlogArticleService blogArticleService;

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
    @GetMapping("/manage/login")
    public String login() {
        return "login";
    }

    /**
     * 后台管理首页
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:53
     */
    @GetMapping("/manage/main")
    public String main() {
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
        model.addAttribute("sysUserList", sysUserList);
        return "backend/userList";
    }

    /**
     * 后台角色管理界面
     *
     * @param
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:53
     */
    @GetMapping("/manage/role")
    public String role() {
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
    @GetMapping("/manage/userInfo")
    public String userInfo() {
        return "backend/userInfo";
    }


    @GetMapping("/manage/article")
    public String article(Model model) {
        List<BlogArticle> blogArticleList = blogArticleService.selectAll();
        for (BlogArticle blogArticle : blogArticleList) {
            blogArticle.setCreater(sysUserService.selectByPrimaryKey(blogArticle.getCreater()).getUserName());
        }
        model.addAttribute("blogArticleList", blogArticleList);
        return "backend/articleList";
    }

    @GetMapping("/manage/articleEdit")
    public String articleEdit() {
        return "backend/articleEdit";
    }

    @GetMapping("/manage/userEdit")
    public String userEdit() {
        return "backend/userEdit";
    }

    @GetMapping("/manage/roleEdit")
    public String roleEdit() {
        return "backend/roleEdit";
    }

    @GetMapping("/manage/pmsnEdit")
    public String pmsnEdit() {
        return "backend/pmsnEdit";
    }

}
