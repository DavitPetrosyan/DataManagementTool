package com.davit.ymlparser;

import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;


@ImportResource("classpath:application-config.xml")
@SpringBootApplication
public class YmlParserApplication
		implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	public static void main(String[] args) {
		SpringApplication.run(YmlParserApplication.class, args);
	}


	@Bean
	public JettyServerCustomizer jettyServerCustomizer() {
		return server -> {
			StatisticsHandler statisticsHandler = new StatisticsHandler();
			statisticsHandler.setServer(server);
			statisticsHandler.setHandler(server.getHandler());
			server.setHandler(statisticsHandler);
		};
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		if (factory instanceof JettyServletWebServerFactory) {
			((JettyServletWebServerFactory) factory).addServerCustomizers(jettyServerCustomizer());
		}
	}

}
