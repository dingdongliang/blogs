package net.htjs.blog.config;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * blog/net.htjs.blog.config
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/13 18:52
 */
@Configuration
public class DruidSpringConfigure {
    /**
     * 定义监听Spring拦截器
     *
     * @return com.alibaba.druid.support.spring.stat.DruidStatInterceptor
     * @author dingdongliang
     * @date 2018/4/18 10:31
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    /**
     * 定义监听Spring切入点
     *
     * @return org.springframework.aop.support.JdkRegexpMethodPointcut
     * @author dingdongliang
     * @date 2018/4/18 10:31
     */
    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        String patterns = "net.htjs.*.*.service.*";
        String patterns2 = "net.htjs.*.*.dao.*";
        pointcut.setPatterns(patterns, patterns2);
        return pointcut;
    }

    /**
     * 定义监听Spring通知类
     *
     * @return org.springframework.aop.Advisor
     * @author dingdongliang
     * @date 2018/4/18 10:31
     */
    @Bean
    public Advisor druidStatAdvisor() {
        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
    }
}
