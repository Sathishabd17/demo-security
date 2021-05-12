package SpringSecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
@ComponentScan(basePackages = { "SpringSecurityConfig" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public MyUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/userone").permitAll()
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/userrole").hasRole("USER")
                .antMatchers("/customrole").hasAnyAuthority("READ_PERMISSION", "WRITE_PERMISSION", "DELETE_PERMISSION")
                .and().formLogin()
                .and().csrf().disable();
    }
}
