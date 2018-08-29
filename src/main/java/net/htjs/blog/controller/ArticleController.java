package net.htjs.blog.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.entity.BaseDomain;
import net.htjs.blog.entity.BlogArticle;
import net.htjs.blog.exception.ResponseData;
import net.htjs.blog.service.BlogArticleService;
import net.htjs.blog.service.SysUserService;
import net.htjs.blog.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * blog/net.htjs.blog.controller
 *
 * @Description: 读取文章信息
 * @Author: dingdongliang
 * @Date: 2018/8/13 16:35
 */
@Controller
@Api(value = "航天金穗研发部技术拾遗-文章操作接口")
@Slf4j
public class ArticleController {
    @Resource
    private BlogArticleService blogArticleService;
    @Resource
    private SysUserService sysUserService;

    @Value("${img.path}")
    private String folder;

    /**
     * 前台首页
     *
     * @param model
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:54
     */
    @GetMapping("/")
    public String index(Model model) {
        List<BlogArticle> blogArticleList = blogArticleService.selectAll();
        for (BlogArticle blogArticle : blogArticleList) {
            blogArticle.setCreater(sysUserService.selectByPrimaryKey(blogArticle.getCreater()).getUserName());
        }
        model.addAttribute("blogArticleList", blogArticleList);
        return "index";
    }


    /**
     * 前台展示文章详细信息界面
     *
     * @param articleId
     * @param model
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/8/22 11:53
     */
    @GetMapping("/detail/{articleId}")
    public String detail(@PathVariable String articleId, Model model) {
        BlogArticle blogArticle = blogArticleService.selectByPrimaryKey(articleId);
        blogArticle.setCreater(sysUserService.selectByPrimaryKey(blogArticle.getCreater()).getUserName());
        model.addAttribute("blogArticle", blogArticle);
        return "front/detail";
    }

    /**
     * 保存文章界面
     *
     * @param blogArticle
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/8/22 11:54
     */
    @PostMapping("/manage/articleSave")
    @ResponseBody
    public ResponseData articleSave(BlogArticle blogArticle) {

        blogArticle.setArticleId(StringUtil.getUUID());

        BaseDomain.createLog(blogArticle);

        blogArticle.setArticleViews(1);

        blogArticleService.insert(blogArticle);
        return ResponseData.success();
    }

    /**
     * 可以改造成JSON格式返回值
     *
     * @param request
     * @param response
     * @param file
     */
    @PostMapping("/uploadfile")
    @ResponseBody
    public Map<String, Object> uploadfile(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>(3);
        try {


            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String newFileName = System.currentTimeMillis() + "." + suffix;

            File localFile = new File(folder, newFileName);
            file.transferTo(localFile);

            String url = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/"))
                    + "/upload/" + newFileName;

            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url", url);
        } catch (Exception e) {
            log.error(e.getMessage());
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            resultMap.put("url", "");
        }
        return resultMap;
    }
}
