package org.webtree.System.Exception;

import org.webtree.System.Log;

/**
 * @author lucifer
 *         Date: 04.11.12
 *         Time: 16:41
 */
public class LoggedError extends WebTreeError {
	public LoggedError(String message) {
		super(message);
		logError(message);
	}

	public LoggedError(String message, Throwable cause) {
		super(message, cause);
		logError(message, cause);
	}

	public LoggedError(Throwable cause) {
		super(cause);
		logError(cause);
	}

	protected void logError(String message, Throwable e){
		Log.getInst().error(message, e);
	}

	protected void logError(Throwable e){
		Log.getInst().error("Error", e);
	}

	protected void logError(String message){
		Log.getInst().error(message);
	}
}
