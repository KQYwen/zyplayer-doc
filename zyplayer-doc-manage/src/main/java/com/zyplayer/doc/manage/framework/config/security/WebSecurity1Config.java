package com.zyplayer.doc.manage.framework.config.security;
//package com.zyplayer.doc.manage.framework.config.security;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//
//@Configuration
//@EnableWebSecurity // 注解开启Spring Security的功能
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()//定义哪些url需要保护，哪些url不需要保护
//			.antMatchers("/statics/lib/**", "/message/").permitAll()//定义不需要认证就可以访问
//			.anyRequest()
//			.authenticated()
//			.and()
//			.formLogin()
//			.loginPage("/statics/manage/login.html")// 定义当需要用户登录时候，转到的登录页面
//			.successHandler(new AuthenticationSuccessHandler() {
//				@Override
//				public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//					httpServletResponse.setContentType("application/json;charset=utf-8");
//					PrintWriter out = httpServletResponse.getWriter();
//					out.write("{\"status\":\"ok\",\"msg\":\"登录成功\"}");
//					out.flush();
//					out.close();
//				}
//			}).failureHandler(new AuthenticationFailureHandler() {
//				@Override
//				public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//					httpServletResponse.setContentType("application/json;charset=utf-8");
//					PrintWriter out = httpServletResponse.getWriter();
//					out.write("{\"status\":\"error\",\"msg\":\"登录失败\"}");
//					out.flush();
//					out.close();
//				}
//			})
//			.permitAll()
//			.loginProcessingUrl("/user/login")
//			.usernameParameter("username")
//			.passwordParameter("password")
//			.permitAll()
//			.and()
//			.logout()
//			.permitAll()
//			.and()
//			.csrf()
//			.disable();
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsServiceImpl());//.passwordEncoder(new Md5PasswordEncoder());
//	}
//
//	@Bean
//	public UserDetailsServiceImpl userDetailsServiceImpl() {
//		return new UserDetailsServiceImpl();
//	}
//
//	@Bean
//	public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
//		MyUsernamePasswordAuthenticationFilter myFilter = new MyUsernamePasswordAuthenticationFilter();
//		myFilter.setAuthenticationManager(authenticationManagerBean());
//		myFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
//		myFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
//		myFilter.setRememberMeServices(tokenBasedRememberMeServices());
//		return myFilter;
//	}
//
//	@Bean
//	public AuthenticationSuccessHandler authenticationSuccessHandler() {
//		return new SimpleUrlAuthenticationSuccessHandler("/login/success");
//	}
//
//	@Bean
//	public AuthenticationFailureHandler authenticationFailureHandler() {
//		return new SimpleUrlAuthenticationFailureHandler("/login/failure");
//	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/statics/lib/**", "**/css/**", "**/js/**", "**/img/**");
//	}
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		//在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为ADMIN
//		auth.inMemoryAuthentication()
//			.withUser("user").password("password").roles("ADMIN");
//	}
//}
//
