package com.example.demo.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfiguration {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(HttpBasicConfigurer::disable)
		.csrf(CsrfConfigurer::disable)
		.cors(Customizer.withDefaults())
		.authorizeHttpRequests((authz) -> authz
						.requestMatchers("/index_admin").hasRole("ADMIN")
						.requestMatchers("/index_emp").hasRole("EMP")
						.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
						.requestMatchers("/auth/**", "/index_**").authenticated()
						.requestMatchers("/", "/join", "/error", "/login", "/idcheck").permitAll())
				.formLogin((login) -> login.loginPage("/loginform")
						.loginProcessingUrl("/login")
						.usernameParameter("id")
						.passwordParameter("pwd")
						.defaultSuccessUrl("/", true).permitAll()
						.successHandler(new MySuccessHandler())
						.failureHandler(new MyFailureHandler())
						);
		return http.build();
	}
}
