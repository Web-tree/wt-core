package org.webtree.System.Exception;

import org.webtree.System.Log;

/**
 * @author lucifer
 *         Date: 04.11.12
 *         Time: 16:41
 */
public class LoggedError extends Error {
	public LoggedError(String message, Throwable cause) {
		super(message, cause);
	}

	public LoggedError(Throwable cause) {
		super("Error!", cause);
	}

	protected void logError(String message, Throwable e){
		Log.getInst().error(message, e);
	}
}
