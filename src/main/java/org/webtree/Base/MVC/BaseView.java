package org.webtree.Base.MVC;

import org.webtree.System.Exception.WebTreeException;

/**
 * Created by IntelliJ IDEA.
 * User: Lucifer
 * Date: 16.04.12
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */
abstract public class BaseView {
	public enum FORMAT {
		HTML, JSON, PLAIN
	}
	protected Object data;
	protected boolean editable;

	abstract public FORMAT getFormat();

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public class ViewException extends WebTreeException{
		public ViewException(Throwable cause) {
			super(cause);
		}
	}
}
