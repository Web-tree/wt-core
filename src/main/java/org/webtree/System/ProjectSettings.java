package org.webtree.System;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * User: lucifer
 * Date: 26.04.12
 * Time: 23:31
 */
public class ProjectSettings {
	final protected static Properties settings = new Properties();
	protected static String confPath;
	static {
		confPath = System.getProperty("user.home") + "/wtconf/";
		if (!new File(confPath).isDirectory()){
			confPath = System.getenv("WEBTREE_CONFPATH");
			if (confPath == null){
				throw new Error("Can't find confPath. Put configs in $HOME/wtconf or set $WEBTREE_CONFPATH");
			}
		}
	}

	public static String getConfPath() throws FileNotFoundException {
		return confPath;
	}

	public static String get(String name) {
//		if (settings == null) {
		init();
//		}
		String result = settings.getProperty(name);
		if (result == null) {
			throw new Error("Property " + name + " not found");
		}
		return result;
	}

	public static boolean isDev(){
		try {
			return Boolean.parseBoolean(get("isDev"));
		}catch (ConfFileNotFound e) {
			return false;
		}
	}

	protected static void init() {
		try {
			synchronized (settings) {
				settings.load(getMainConfFile());
			}
		} catch (IOException e) {
			throw new Error("Property file not found");
		}
	}

	protected static FileInputStream getMainConfFile() throws FileNotFoundException {
		try {
			return new FileInputStream(getConfPath() + "main.properties");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			throw new ConfFileNotFound();
		}
	}
	protected static class ConfFileNotFound extends Error{}
}
