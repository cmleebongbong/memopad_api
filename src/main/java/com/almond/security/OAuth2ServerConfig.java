package com.almond.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class OAuth2ServerConfig extends ResourceServerConfigurerAdapter {
	
	/**
	 * API 서버
	 * 
	 */
	@EnableResourceServer
	public class ApiServer {
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
		
		@Bean
		public TokenStore jdbcTokenStore(DataSource dataSource) {
			return new JdbcTokenStore(dataSource);
		}
	}
}


