package com.lagou.config;

import com.lagou.domain.Permission;
import com.lagou.filter.ValidateCodeFilter;
import com.lagou.handler.MyAccessDeniedHandler;
import com.lagou.service.PermissionService;
import com.lagou.service.impl.MyAuthenticationService;
import com.lagou.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

/**
 * @BelongsProject lagou-security-management
 * @Author lengy
 * @CreateTime 2022/8/17 23:26
 * @Description Spring Security 配置类
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 开启注解支持
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private MyAuthenticationService myAuthenticationService;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private PermissionService  permissionService;

    /**
     * 身份安全管理器
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 查询数据库，将信息封装成 security 的user 对象并交给框架，让框架来完成身份的验证
        auth.userDetailsService(myUserDetailsService);
    }

    /**
     * 设置一些请求可以忽略安全控制，那些请求不需要拦截就可以在WebSecurity设置
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 解决静态资源被拦截的问题
        // 将这些静态资源忽略掉，可以不需要认证就可以直接访问了
        web.ignoring().antMatchers("/css/**","/images/**","/js/**","/code/**");
    }

    /**
     *  处理HTTP的请求方法
     *  当设置一些需要安全控制请求时，在HttpSecurity里设置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.httpBasic().   // 开启http basic认证
            and().authorizeRequests().anyRequest().authenticated(); // 所有请求都需要认证之后才能访问*/

        // 加入用户名密码验证过滤器的前面
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);


        // 设置/user开头的请求需要admin权限
        //http.authorizeRequests().antMatchers("/user/**").hasRole("ADMIN");


        // 以/product 开头的请求需要有ADMIN或PRODUCT的权限并且ip是127.0.0.1
        // 设置/product开头的请求需要有ADMIN 或者 PRODUCT权限，并且访问的IP是127.0.0.1
        /*http.authorizeRequests().antMatchers("/product/**").access(
            "hasAnyRole('ADMIN','PRODUCT')and hasIpAddress('127.0.0.1')");*/


        // 使用自定义Bean完成授权
        /*http.authorizeRequests().antMatchers("/user/{id}")
            .access("@myAuthorizationService.check(authentication,request,#id)");*/


        // 查询数据库所有权限列表
        List<Permission> list = permissionService.list();
        for (Permission permission : list) {
            // 添加请求权限
            http.authorizeRequests().antMatchers(permission.getPermissionUrl())
                .hasAuthority(permission.getPermissionTag());
        }



        // 设置权限不足的信息
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);



        // 将登录页面由原来的spring security 默认的登录页面，改成自己定义的登录页面
        http.formLogin()    // 开启表单认证
                .loginPage("/toLoginPage")  // 自定义登录页面
                .loginProcessingUrl("/login") // 表单提交的路径
                .usernameParameter("username")
                .passwordParameter("password")  // 自定义input的那name值
                .successForwardUrl("/")          // 登录成功之后跳转的路径
                .successHandler(myAuthenticationService)
                .failureHandler(myAuthenticationService)   // 登录成功或失败的处理
                .and().logout().logoutUrl("/logout")
                .logoutSuccessHandler(myAuthenticationService)
                .and().rememberMe()             // 开启记住我功能
                .tokenValiditySeconds(1209600)         // token失效时间，默认是2周
                .rememberMeParameter("remember-me")     // 自定义表单input值
                .tokenRepository(getPersistentTokenRepository())
                .and().authorizeRequests().antMatchers("/toLoginPage").permitAll()   // 放行登录页面
                .anyRequest().authenticated();  // 所有请求都需要登录认证才能访问

        // 关闭csrf防护
        http.csrf().disable();

        // 开启csrf防护，定义哪些路径不需要防护
        //http.csrf().ignoringAntMatchers("/user/saveOrUpdate");
        // 加载同源域名下iframe页面
        http.headers().frameOptions().sameOrigin();

        /*http.sessionManagement()   // 设置session管理
        .invalidSessionUrl("/toLoginPage")    // session失效之后跳转的路径
        .maximumSessions(1)              // session最大会话数量 1，同一时间只能有一个用户登录  互踢
        .maxSessionsPreventsLogin(true) // 如果达到最大会话数量，就阻止登录
        .expiredUrl("/toLoginPage");    // session过期之后跳转的路径*/


        // 开启跨域支持
        http.cors().configurationSource(corsConfigurationSource());
    }

    @Autowired
    private DataSource dataSource;

    /**
     * 负责token与数据库之间的操作
     * @return
     */
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){

        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);  // 设置数据源
        //tokenRepository.setCreateTableOnStartup(true);  // 启动时帮我门自动创建一张表，第一次启动设置true 第二次启动设置false或者注释

        return tokenRepository;
    }


    /**
     * 跨域配置信息源
     */
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 配置允许跨域的站点
        corsConfiguration.addAllowedOrigin("*");

        // 允许跨域的http方法
        corsConfiguration.addAllowedMethod("*");

        // 允许跨域的请求头
        corsConfiguration.addAllowedHeader("*");

        // 允许带凭证
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        // 对所有url都生效
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }
}
