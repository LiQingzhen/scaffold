package com.copote.coop.config;

import com.copote.coop.auth.*;
import com.copote.coop.auth.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @Author LQZ
 * @Date 2020/4/20 14:07
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    @Autowired
    AuthenticationFailureHandlerImpl authenticationFailureHandler;
    @Autowired
    LogoutSuccessHandlerImpl logoutSuccessHandler;
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置认证方式等
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http相关的配置，包括登入登出、异常处理、会话管理等
        http.cors().and().csrf().disable();
        http.
//                csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                    .ignoringAntMatchers("/authentication").
//                and().cors().
//                and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class).
                authorizeRequests().
                    antMatchers("/getUser").hasAuthority("query_user").

                and().exceptionHandling().
                    authenticationEntryPoint(authenticationEntryPoint).

                and().formLogin().
                    permitAll().
                    successHandler(authenticationSuccessHandler).
                    failureHandler(authenticationFailureHandler).

                and().logout().
                    permitAll().
                    logoutSuccessHandler(logoutSuccessHandler).
                    deleteCookies("JSESSIONID").

                and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8888"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
