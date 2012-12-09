package org.webtree.Page.Model;

import org.webtree.Base.BaseModule.BaseModuleModel;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Lucifer
 * Date: 16.04.12
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class PageModel extends BaseModuleModel {
	protected int id;

	protected String header;
	protected String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public HashMap<String, String> toHashMap() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("header", header);
		hashMap.put("content", content);
		return hashMap;
	}
}
