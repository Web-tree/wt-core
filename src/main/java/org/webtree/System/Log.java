package org.webtree.System;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * @author lucifer
 *         Date: 15.05.12
 *         Time: 8:32
 */
public class Log {
	protected static HashMap<String, Logger> loggers = new HashMap<>();

	public static Logger getInst() {
		String name;
		try {
			name = ProjectSettings.get("isDev").equals("true") ? "dev" : "production";
		} catch (Throwable e) {
			name = "production";
			e.printStackTrace();
		}
		try {
			PropertyConfigurator.configure(ProjectSettings.getConfPath() + "log4j.properties");
		} catch (FileNotFoundException e) {
			throw new Error(e);
		}
		return getInst(name);
	}

	public static Logger getInst(String name) {
		if (!loggers.containsKey(name)) {
			loggers.put(name, LoggerFactory.getLogger(name));
		}
		return loggers.get(name);
	}
}