package org.webtree.Human.Model;

import org.webtree.Base.BaseModule.BaseModuleModel;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Lucifer
 * Date: 16.04.12
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class HumanModel extends BaseModuleModel {
	protected int id;
    protected int rate;
    protected String name;

    public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HumanModel() {
	}

	public HumanModel(String name) {
		this.name = name;
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("name", name);
		hashMap.put("rate", rate);
		return hashMap;
	}
}
