package org.webtree.Base.MVC;

import org.webtree.System.Exception.WebTreeException;

import java.util.HashMap;
import java.util.Map;

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
	protected Map<String, Object> data;

	abstract public FORMAT getFormat();

	public BaseView(){
		data = new HashMap<>();
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Object data) {
		this.data.put("content", data);
	}

	public class ViewException extends WebTreeException{
		public ViewException(Throwable cause) {
			super(cause);
		}
	}
}
