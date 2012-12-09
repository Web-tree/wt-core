package org.webtree.Page.View;

import org.webtree.Base.BaseModule.BaseModuleView;
import org.webtree.Page.Model.PageModel;

/**
 * User: Lucifer
 * Date: 16.04.12
 * Time: 13:23
 */
public class PageView extends BaseModuleView {
	PageModel model;

	String template;

	public PageModel getModel() {
		return model;
	}

	public void setModel(PageModel model) {
		this.model = model;
	}

    @Override
    protected String templateName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected String templateDirectory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	protected String parseView(){
		return processTemplate(getData(), getTemplate(template, "module/page"));
	}

	public PageView(FORMAT format, String template) {
		this.format = format;
		this.template = template;
	}
}
