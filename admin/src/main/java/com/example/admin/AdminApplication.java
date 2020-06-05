package com.example.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.codecentric.boot.admin.server.cloud.discovery.ServiceInstanceConverter;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.utils.jackson.InstanceIdMixin;
import de.codecentric.boot.admin.server.utils.jackson.RegistrationDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableAdminServer
@Configuration
@EnableDiscoveryClient
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		// This line below is the magic one for me. Obviously, if need to switch properties to true, use featuresToEnable
		builder.featuresToDisable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
		return builder;
	}


	@Configuration
	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
		}
	}

	@Bean
	public ObjectMapper objectMapper(){
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("RegistrationDeserializer", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(Registration.class, new RegistrationDeserializer());
		objectMapper.registerModule(module);
		objectMapper.addMixIn(InstanceId.class, InstanceIdMixin.class);
		return objectMapper;
	}

	@Bean
	public ServiceInstanceConverter serviceInstanceConverter(){
		return new MyEurekaServiceInstanceConverter();
	}

}
