package com.yooni.tools;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class LogHelper {
	// 日志文件路径
	public static Logger gLogger;

	public static void configLog() {
		String SDPath = Environment.getExternalStorageDirectory().getPath()
				+ "/yoonilog/";

		final LogConfigurator logConfigurator = new LogConfigurator();

		logConfigurator.setFileName(SDPath + "yn" + System.currentTimeMillis()
				+ ".log");
		// Set the root log level
		logConfigurator.setRootLevel(Level.DEBUG);
		// Set log level of a specific logger
		logConfigurator.setLevel("org.apache", Level.ERROR);

		logConfigurator.configure();
		gLogger = Logger.getLogger("CrifanLiLog4jTest");
	}
}
