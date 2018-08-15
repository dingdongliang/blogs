package net.htjs.blog.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.entity.BlogArticle;
import net.htjs.blog.service.BlogArticleService;
import net.htjs.blog.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * blog/net.htjs.blog.controller
 *
 * @Description: 读取文章信息
 * @Author: dingdongliang
 * @Date: 2018/8/13 16:35
 */
@Controller
@Api(value = "航天金穗大数据组技术博客-文章操作接口")
@Slf4j
public class ArticleController {
    @Resource
    private BlogArticleService blogArticleService;

    @GetMapping("/")
    public String index(Model model) {
        List<BlogArticle> blogArticleList = blogArticleService.selectAll();
        model.addAttribute("blogArticleList", blogArticleList);
        return "index";
    }

    @GetMapping("/sort")
    public String sort() {
        return "sort";
    }

    @GetMapping("/detail/{articleId}")
    public String detail(@PathVariable String articleId, Model model) {
        BlogArticle blogArticle = blogArticleService.selectByPrimaryKey(articleId);
        model.addAttribute("blogArticle", blogArticle);
        return "detail";
    }

    @GetMapping("/edit")
    public String edit() {
        return "edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@RequestBody BlogArticle blogArticle) {
        log.info(blogArticle.getArticleInfo());

        blogArticle.setArticleId(StringUtil.getUUID());
        blogArticle.setCreater("1d55fc9860894fbc88d7d79085e2f55f");
        blogArticle.setModifyer("1d55fc9860894fbc88d7d79085e2f55f");
        blogArticle.setArticleViews(1);

        blogArticleService.insert(blogArticle);
        return "OK";
    }

    @PostMapping("/uploadfile")
    public void uploadfile(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String rootPath = request.getSession().getServletContext().getRealPath("/upload/");

            /**
             * 文件路径不存在则需要创建文件路径
             */
            File filePath = new File(rootPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            //最终文件名
            File realFile = new File(rootPath + File.separator + attach.getOriginalFilename());
            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);

            //下面response返回的json格式是editor.md所限制的，规范输出就OK
            response.getWriter().write("{\"success\": 1, \"message\":\"上传成功\",\"url\":\"/resources/upload/"
                    + attach.getOriginalFilename() + "\"}");
        } catch (Exception e) {
            try {
                response.getWriter().write("{\"success\":0}");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
