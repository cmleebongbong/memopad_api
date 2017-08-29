package com.almond.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class OAuth2ServerConfig {
	
	/**
	 * API 서버
	 * 
	 */
	@EnableResourceServer
	public class ApiServer extends ResourceServerConfigurerAdapter {
		/*@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/api/**").authenticated();
		}*/
	}

	/**
	 * OAuth2 서버
	 *
	 */
	@EnableAuthorizationServer
	public class OAuth2Server {
		
		/*@Bean
		public TokenStore jdbcTokenStore(DataSource dataSource) {
			return new JdbcTokenStore(dataSource);
		}*/
	}
}


