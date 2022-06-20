package com.ouyang.boot.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-03-01 15:14
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 设置某些页面需要的权限
     * @param security
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2");
        //没有权限会被拒绝并返回登陆页面 默认/login ,前提这个页面能访问
        security.formLogin().loginPage("/login");

        security.logout().logoutSuccessUrl("/");
        // logout to where
        security.rememberMe().rememberMeParameter("记住我");
    }

    /**
     * 手动设置login里某些用户的角色,而不是从数据库中获取信息
     * 其中密码需要加密
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("oyy").password(encode("123")).roles("vip1")
                .and()
                .withUser("liu").password(encode("123")).roles("vip1", "vip2");
    }

    protected String encode(String password) {

        return new BCryptPasswordEncoder().encode(password);
    }




}
