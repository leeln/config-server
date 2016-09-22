package org.leeln.config.server;

import com.netflix.config.ConfigurationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
//@EnableConfigServer
@EnableHystrix
public class ConfigServerApplication {

	public static void main(String[] args) {
		ConfigurationManager.getConfigInstance().setProperty("hystrix.threadpool.default.coreSize", "200");

		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
