package config;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
	import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

	@Configuration
	public class TaskConfig extends WebSecurityConfigurerAdapter{
		@Autowired
	    public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
	            throws Exception {
	        auth.inMemoryAuthentication().withUser("yashu").password("dummy")
	                .roles("USER", "ADMIN");
	    }
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests().antMatchers("/todo/wow").permitAll();
	    }
	}