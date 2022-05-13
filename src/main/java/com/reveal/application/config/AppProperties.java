package com.reveal.application.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

public class AppProperties {
	
	private static final Logger LOGGER = Logger.getLogger(AppProperties.class.getName());
	
	//unsure how well this will work for other uses launching
	private static Path path = Paths.get(".","src","main","resources","app.properties");
	private static Properties appProps = new Properties();
	
	public static String getProperty(String key) {
		return appProps.getProperty(key);
	}

	public static void loadProperties() {
		LOGGER.info("Loading app.properties from: "+path);
		try {
			try(FileInputStream io = new FileInputStream(path.toString())) {
				appProps.load(io);
				LOGGER.info("App.properties loaded.");
			}
		} catch (IOException e) {
			LOGGER.severe("Failed to load app properties. path="+path);
			e.printStackTrace();
		}	
	}
	

}
