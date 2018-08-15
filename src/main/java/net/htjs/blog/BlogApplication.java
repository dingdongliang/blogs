package net.htjs.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * blog/net.htjs
 *
 * @Description: 启动类
 * @Author: dingdongliang
 * @Date: 2018/8/13 16:41
 */
@MapperScan("net.htjs.blog.dao")
@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
