package ru.diasoft.micro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {

//    @Value("${security.enabled:true}")
    @Value("false")
    private boolean securityEnabled;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if(securityEnabled) {
            http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/csrf", "/v2/api-docs", "/swagger-resources/configuration/ui", "/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
            .antMatchers("/**").authenticated();
        } else {
            http.authorizeRequests().antMatchers("/**").permitAll();
        }
    }
}
