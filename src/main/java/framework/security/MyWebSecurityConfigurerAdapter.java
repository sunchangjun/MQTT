package framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import framework.core.utils.MD5Transform;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Bean
	UserDetailsService myUserDetailsService() {
		// 注册UserDetailsService 的bean
		return new MyUserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// user Details
		// Service验证
		auth.userDetailsService(myUserDetailsService()).passwordEncoder(new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return MD5Transform.EncoderByMd5((String) rawPassword, "utf8");
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.equals(MD5Transform.EncoderByMd5((String) rawPassword, "utf8"));
			}
		});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 任何请求,登录后可以访问
		http.authorizeRequests();
		// .anyRequest().authenticated();
		// 登录页面用户任意访问
		http.formLogin().loginPage("/front/admin/login").loginProcessingUrl("/j_spring_security_check")
				.usernameParameter("j_username").passwordParameter("j_password").defaultSuccessUrl("/front/admin/index")
				.failureUrl("/login?error").permitAll();
		// 注销行为任意访问
		http.logout().permitAll();
		http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
		http.csrf().disable();
	}
}