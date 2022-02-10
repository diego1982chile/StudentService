package cl.dsoto.StudentService;

import cl.dsoto.StudentService.filters.JWTAuthorizationFilter;
import io.micrometer.influx.InfluxConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Duration;

@SpringBootApplication
@EnableFeignClients("cl.dsoto.StudentService.services.external.impl.proxies")
public class StudentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
		//SpringApplication springApplication = new SpringApplication(StudentServiceApplication.class);
		//springApplication.addListeners(new ApplicationPidFileWriter());     // register PID write to spring boot. It will write PID to file
		//springApplication.run(args);
	}

	/*
	@Bean
	InfluxConfig influxConfig() {
		return new InfluxConfig() {
			@Override
			public String get(String key) {
				return null;
			}

			@Override
			public Duration step() {
				return Duration.ofSeconds(20);
			}

			@Override
			public String org() {
				return "org";
			}

			@Override
			public String bucket() {
				return "metrics";
			}

			@Override
			public String token() {
				return "my-token";
			}
		};
	}
	*/

	/*
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/users/authenticate").permitAll()
					.antMatchers(HttpMethod.POST, "/users/new").permitAll()
					.anyRequest().authenticated();
		}
	}
	*/


}
