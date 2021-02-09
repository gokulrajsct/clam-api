package nz.co.datacom.mlc.api.clamav;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan

/**
 * Simple Spring Boot application which acts as a REST endpoint for clamd
 * server.
 */
public class Application {

	@Value("${clamd.maxfilesize}")
	private String maxfilesize;

	@Value("${clamd.maxrequestsize}")
	private String maxrequestsize;

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("clamd.host", "0.0.0.0");
		defaults.put("clamd.port", 3310);
		defaults.put("clamd.timeout", 500);
		defaults.put("clamd.maxfilesize", "20000KB");
		defaults.put("clamd.maxrequestsize", "20000KB");
		app.setDefaultProperties(defaults);
		app.run(args);
	}
}
