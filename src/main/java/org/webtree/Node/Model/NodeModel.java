package org.webtree.Node.Model;

import org.webtree.Base.BaseModule.BaseModuleModel;
import org.webtree.System.Exception.WebTreeException;

import java.util.HashMap;

/**
 * @author Lucifer
 *         User: Lucifer
 *         Date: 16.04.12
 *         Time: 21:59
 */
public class NodeModel extends BaseModuleModel {
	protected int id;
	protected int ownerId;
	protected String title;
	protected int rate;

	public NodeModel() {
	}

	public NodeModel(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws NodeModelError {
		if (title == "") {
			throw new NodeModelError("Заголовок не может быть пустым");
		}
		this.title = title;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public void validate() throws ValidationError {
		if ((title == null) || (ownerId == 0)) {
			throw new ValidationError();
		}
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("ownerId", Integer.toString(ownerId));
		return hashMap;
	}

	public static class ValidationError extends WebTreeException {
	}

	public static class NodeModelError extends WebTreeException {
		/**
		 * Constructs a new exception with {@code null} as its detail message.
		 * The cause is not initialized, and may subsequently be initialized by a
		 * call to {@link #initCause}.
		 */
		public NodeModelError() {
		}

		/**
		 * Constructs a new exception with the specified detail message.  The
		 * cause is not initialized, and may subsequently be initialized by
		 * a call to {@link #initCause}.
		 *
		 * @param message the detail message. The detail message is saved for
		 *                later retrieval by the {@link #getMessage} method.
		 */
		public NodeModelError(String message) {

			super(message);
		}
	}
}
