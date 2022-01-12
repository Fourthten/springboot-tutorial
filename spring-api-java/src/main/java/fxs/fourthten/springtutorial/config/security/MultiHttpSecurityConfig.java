package fxs.fourthten.springtutorial.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity @RequiredArgsConstructor
public class MultiHttpSecurityConfig {

    @Configuration
    @Order(1)
    public static class AuthSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private final PasswordEncoder passwordEncoder;

        public AuthSecurityConfigurationAdapter(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors().and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().antMatcher("/auth/**")
                    .authorizeRequests(authorize -> authorize
                        .antMatchers(
                            "/auth/login"
                        ).permitAll()
                        .anyRequest().authenticated()
                    );
//                    .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
            http.oauth2ResourceServer()
                    .jwt()
                    .jwtAuthenticationConverter(authenticationConverter());
        }

        @Bean @Override
        protected UserDetailsService userDetailsService() {
            UserDetails userAuth = User
                    .withUsername("userauth")
                    .authorities("USER")
                    .passwordEncoder(passwordEncoder::encode)
                    .password("1234")
                    .build();

            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(userAuth);
            return manager;
        }

        protected JwtAuthenticationConverter authenticationConverter() {
            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            authoritiesConverter.setAuthorityPrefix("");
            authoritiesConverter.setAuthoritiesClaimName("roles");

            JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
            converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
            return converter;
        }
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        PasswordEncoder passwordEncoder;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorize -> authorize
                        .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                        .antMatchers("/admin").hasRole("ADMIN")
                        .antMatchers("/").permitAll()
//                        .anyRequest().authenticated()
                    )
                    .formLogin();
//                    .authorizeRequests()
//                    .antMatchers("/user").hasAnyRole("ADMIN", "USER")
//                    .antMatchers("/admin").hasRole("ADMIN")
//                    .antMatchers("/").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin();
//                    .loginPage("/login").permitAll()
//                    .and()
//                    .logout().permitAll();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .withUser("admin")
                    .password(passwordEncoder.encode("1234"))
//                    .password("{noop}1234")
                    .roles("ADMIN")
                    .and()
                    .withUser("user")
//                    .password("{noop}1234")
                    .password(passwordEncoder.encode("1234"))
                    .roles("USER");
        }
    }


}