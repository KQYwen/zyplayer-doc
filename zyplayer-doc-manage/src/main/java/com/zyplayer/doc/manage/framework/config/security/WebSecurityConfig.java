package com.zyplayer.doc.manage.framework.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Order(1)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * 忽略静态文件
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String loginPage = "/static/manage/login.html";
		// 无需登录即可访问的接口
		String[] permitAllAntPatterns = {
			// 登录接口
			"/login/**",
			// 开放接口的静态文件和接口
			"/open-doc.html", "/webjars/open-doc/**", "/swagger-mg-ui/open-doc/**",
			// http代理请求接口，有白名单限制，也不怕随便请求到内网资源了
			"/swagger-mg-ui/http/**",
			// 静态资源
			"/webjars/zui/**", "/webjars/vue/**", "/static/lib/**"
		};
		// 文档页面需要具有文档权限
		String[] docAntPatterns = {
			"/document.html", "/doc-db.html", "/doc.html", "/swagger-ui.html",
			"/swagger-mg-ui/document/**", "/swagger-mg-ui/storage/**", "/swagger-resources/**"
		};
		http.authorizeRequests()
			.antMatchers(permitAllAntPatterns).permitAll()
			.antMatchers(docAntPatterns).hasAuthority("DOC_ALL")
			// 其他地址的访问均需登录
			.anyRequest().authenticated().and()
			// 添加验证码验证
			.addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling()
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(loginPage))
			.and().addFilterAt(rememberMeAuthenticationFilter(), RememberMeAuthenticationFilter.class)
			// 指定登录页面的请求路径
			.formLogin().loginPage(loginPage)
			// 登陆处理路径
			.loginProcessingUrl("/login").permitAll()
			// 退出请求的默认路径为logout
			.and().logout().deleteCookies("remember-me")
			.logoutUrl("/logout").logoutSuccessUrl(loginPage)
			.permitAll()
			// 开启rememberMe，设置一个私钥专供testall项目使用，注意与下面TokenBasedRememberMeServices的key保持一致
			// .rememberMe().key("testallKey").and()
			// 关闭csrf
			.and().csrf().disable()
			// X-Frame-Options: SAMEORIGIN 表示该页面可以在相同域名页面的 frame 中展示
			.headers().frameOptions().sameOrigin();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl())
		.passwordEncoder(new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
			}
			@Override
			public boolean matches(CharSequence charSequence, String s) {
				String digestAsHex = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
				return Objects.equals(s, digestAsHex);
			}
		}).and().authenticationProvider(rememberMeAuthenticationProvider());
	}
	
	@Bean
	public DocDetailsServiceImpl userDetailsServiceImpl() {
		return new DocDetailsServiceImpl();
	}

	@Bean
	public DocUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
		DocUsernamePasswordAuthenticationFilter myFilter = new DocUsernamePasswordAuthenticationFilter();
		myFilter.setAuthenticationManager(authenticationManagerBean());
		myFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		myFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		myFilter.setRememberMeServices(tokenBasedRememberMeServices());
		return myFilter;
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SimpleUrlAuthenticationSuccessHandler("/login/success");
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/login/failure");
	}

	@Bean
	public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
		TokenBasedRememberMeServices tbrms = new TokenBasedRememberMeServices("testallKey", userDetailsServiceImpl());
		// 设置cookie过期时间为2天
		tbrms.setTokenValiditySeconds(60 * 60 * 24 * 2);
		// 设置checkbox的参数名为rememberMe（默认为remember-me），注意如果是ajax请求，参数名不是checkbox的name而是在ajax的data里
		tbrms.setParameter("rememberMe");
		tbrms.setAlwaysRemember(false);
		return tbrms;
	}

	@Bean
	public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
		RememberMeAuthenticationProvider rmap = new RememberMeAuthenticationProvider("testallKey");
		return rmap;
	}

	@Bean
	public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
		RememberMeAuthenticationFilter myFilter = new RememberMeAuthenticationFilter(authenticationManagerBean(), tokenBasedRememberMeServices());
		return myFilter;
	}
	
}
