package org.webtree.System.Exception;

/**
 * @author Max Levicky
 *         Date: 15.03.14
 *         Time: 16:37
 */
public class WebTreeRuntimeException extends RuntimeException {
    public WebTreeRuntimeException() {
    }

    public WebTreeRuntimeException(String message) {
        super(message);
    }

    public WebTreeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebTreeRuntimeException(Throwable cause) {
        super(cause);
    }

    public WebTreeRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
