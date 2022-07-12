package com.example.courseproject.config;

import com.example.courseproject.enums.Role;
import com.example.courseproject.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class  WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_ENDPOINTS = {"/admin", "/admin/**"};
    private static final String[] USER_ENDPOINTS = {"/collection/new", "/collection/update", "/collection/remove", "/tags/add", "/item/new", "/item/update", "/item/addlike/*", "/item/removelike/*"};
    private static final String[] OPEN_ENDPOINTS = {"/", "/signup", "/auth", "/search/*", "/collection", "/tags/**", "/item/id=*", "/item/collection-id=*", "/item/latest/*", "/comment/all/item-id=*",
                                                    "/collection/top/*", "/collection/byItemId=*", "/item/likes/*",};

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfiguration(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();

        http.authorizeRequests()
                .antMatchers(ADMIN_ENDPOINTS)
                .hasAuthority(Role.ADMIN.toString())
                .antMatchers(USER_ENDPOINTS)
                .hasAnyAuthority(Role.USER.toString(), Role.ADMIN.toString())
                .antMatchers(OPEN_ENDPOINTS)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable() // disabling CSRF will allow sending POST request using Postman
                .httpBasic();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
