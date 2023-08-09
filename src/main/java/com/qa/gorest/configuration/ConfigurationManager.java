package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkexceptions.APIFrameworkException;

public class ConfigurationManager {
	private Properties prop;
	private FileInputStream ip;

	public Properties initprop() {
		prop = new Properties();
		// maven : cmd line argument
		// mvn clean install -Denv="stage"

		String envName = System.getProperty("env");

		try {
			if (envName == null) {
				System.out.println("no env is given...hence running test on the QA env...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				System.out.println("Running tests on env: " + envName);

				switch (envName) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Please pass the right env name....." + envName);
					throw new APIFrameworkException("No ENV IS GIVEN");

				}
			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {

			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

}
