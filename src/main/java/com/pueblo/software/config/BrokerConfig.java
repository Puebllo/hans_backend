package com.pueblo.software.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import io.moquette.interception.InterceptHandler;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.pueblo.software.mqtt.listener.PublisherListener;


@Component
public class BrokerConfig {

	private static final Logger LOG = Logger.getLogger(BrokerConfig.class);
		
	@Autowired
	PublisherListener publisherListener;
		
	@Bean
	public Server start() throws InterruptedException, IOException {
		IResourceLoader classpathLoader = new ClasspathResourceLoader();
		final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);

		Server mqttBroker = new Server();
		final List<? extends InterceptHandler> userHandlers = Arrays.asList(publisherListener);
		mqttBroker.startServer(classPathConfig, userHandlers);

		LOG.info("moquette mqtt broker started, press ctrl-c to shutdown..");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("stopping moquette mqtt broker..");
				mqttBroker.stopServer();
				System.out.println("moquette mqtt broker stopped");
			}
		});
		return mqttBroker;
	}
	

}
