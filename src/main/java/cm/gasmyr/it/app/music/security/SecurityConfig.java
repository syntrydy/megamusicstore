package cm.gasmyr.it.app.music.security;

import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired 
	private MyAuthenticationSuccessHandler successHandler;
	

	@Bean
	public PasswordEncoder passwordEncoder(StrongPasswordEncryptor passwordEncryptor) {
		PasswordEncoder passwordEncoder = new PasswordEncoder();
		passwordEncoder.setPasswordEncryptor(passwordEncryptor);
		return passwordEncoder;
	}
	
	@Bean
	public StrongPasswordEncryptor strongEncryptor() {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		return encryptor;
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}

	@Autowired
	public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder) {
		authenticationManagerBuilder.authenticationProvider(
				daoAuthenticationProvider(passwordEncoder(strongEncryptor()), userDetailsService));
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
				.antMatchers("/api/users", "/api/reset", "/", "/css/**", "/js/**", "/fonts/**", "/images/**","/public/**","/public/css/**","/public/images/**","/public/js/**","/public/fonts/**")
				.permitAll();
		httpSecurity.authorizeRequests().antMatchers("/welcome").hasAnyRole("ADMIN", "USER");
		httpSecurity.authorizeRequests()
				.antMatchers("/home", "/users", "/roles", "/groups", "/category", "/categories", "/music", "/musics")
				.hasAnyRole("ADMIN");
		httpSecurity.authorizeRequests().antMatchers("/api/user").hasAnyRole("ADMIN");
		httpSecurity.authorizeRequests().antMatchers("/api/role").hasAnyRole("ADMIN");
		httpSecurity.authorizeRequests().antMatchers("/api/group").hasAnyRole("ADMIN");
		httpSecurity.authorizeRequests().and().formLogin().loginPage("/").successHandler(successHandler).usernameParameter("username")
				.passwordParameter("password").failureUrl("/");
		httpSecurity.authorizeRequests().and().logout().invalidateHttpSession(true).logoutSuccessUrl("/").permitAll();
		httpSecurity.authorizeRequests().anyRequest().authenticated();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(false);
	}
}
