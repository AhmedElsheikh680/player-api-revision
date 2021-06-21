package com.spring.player.config;


import com.spring.player.jwt.JwtAuthorizationFilter;
import com.spring.player.repo.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPricipleDetailsService userPricipleDetailsService;
    private UserRepo userRepo;


    public SecurityConfiguration(UserPricipleDetailsService userPricipleDetailsService, UserRepo userRepo) {
        this.userPricipleDetailsService = userPricipleDetailsService;
        this.userRepo = userRepo;
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                authenticationProvider(authenticationProvider());

//        auth
//             .inMemoryAuthentication()
//             .withUser("ahmed")
//             .password(passwordEncoder().encode("ahmed123"))
////             .roles("ADMIN")
//             .authorities("ACCESS_BASIC1", "ROLE_ADMIN")
//             .and()
//             .withUser("yasser")
//             .password(passwordEncoder()
//             .encode("yasser123"))
////             .roles("MANAGER")
//             .authorities("ACCESS_BASIC2", "ROLE_MANAGER")
//             .and()
//             .withUser("kareem").password(passwordEncoder().encode("kareem123")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepo))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers("/api/v1/admin").hasRole("ADMIN")
                .antMatchers("/api/v1/adminormanger").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/api/v1/adminmangeruser").hasAnyRole("ADMIN", "MANAGER", "USER")
                .antMatchers("/api/v1/players").hasRole("ADMIN")
                .anyRequest().authenticated();



//        http
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepo))
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
////                .anyRequest().authenticated()
//                .antMatchers("/api/v1/main").permitAll()
//                .antMatchers("/api/v1/profile").authenticated()
//                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                .antMatchers("/api/v1/management").hasAnyRole("ADMIN", "MANAGER")
//                .antMatchers("api/v1/basic/mybasic").hasAuthority("ACCESS_BASIC1")
//                .antMatchers("/api/v1/basic/allbasic").hasAuthority("ACCESS_BASIC2")
//                .anyRequest().authenticated();


//                .and()
//                .formLogin()
//                .loginProcessingUrl("/signin")
//                .loginPage("/api/v1/login")
//                .usernameParameter("user")
//                .passwordParameter("pass")
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/api/v1/main");
//                .httpBasic();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPricipleDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
