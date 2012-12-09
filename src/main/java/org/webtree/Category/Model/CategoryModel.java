package org.webtree.Category.Model;

import org.webtree.Base.BaseModule.BaseModuleModel;

/**
 * @author lucifer
 *         Date: 11.07.12
 *         Time: 9:09
 */
public class CategoryModel extends BaseModuleModel {
	protected int id;
	protected String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
