package org.webtree.System.Exception;

import net.sf.json.JSONObject;

/**
 * @author lucifer
 *         Date: 30.06.12
 *         Time: 18:45
 */
abstract public class MessageException extends WebTreeRuntime {
	protected String name;

	abstract public String getStatus();

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("status", getStatus());
		json.put("message", getMessage());
		return json;
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	public static class InfoMessage extends MessageException {
		public String getStatus() {
			return "info";
		}

		public InfoMessage(String message) {
			super(message);
		}
	}

	public static class NoticeMessage extends MessageException {
		public String getStatus() {
			return "notice";
		}

		public NoticeMessage(String message) {
			super(message);
		}
	}

	public static class WarningMessage extends MessageException {
		public String getStatus() {
			return "warning";
		}

		public WarningMessage(String message) {
			super(message);
		}
	}

	public static class ErrorMessage extends MessageException {
		public String getStatus() {
			return "error";
		}

		public ErrorMessage(String message) {
			super(message);
		}
	}
}