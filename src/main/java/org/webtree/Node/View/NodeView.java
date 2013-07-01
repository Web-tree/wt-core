package org.webtree.Node.View;

import org.webtree.Base.BaseModule.BaseModuleView;
import org.webtree.Base.BaseModule.Editable;
import org.webtree.Link.Model.LinkModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lucifer
 *         Date: 16.04.12
 *         Time: 22:01
 */
abstract public class NodeView extends BaseModuleView implements Editable {

	protected boolean editable;

	protected List<LinkModel> getMenu() {
		ArrayList<LinkModel> linkModels = new ArrayList<>();
		String basePath = "node/";
		linkModels.add(new LinkModel(basePath + "list", "Все ноды"));
		linkModels.add(new LinkModel(basePath + "list/my", "Мои ноды"));
		linkModels.add(new LinkModel(basePath + "create", "Создать нод"));
		return linkModels;
	}

	@Override
	protected Map<String, Object> getDefaultMap() {
		Map<String, Object> map = super.getDefaultMap();
		map.put("nodeMenu", getMenu());
		return map;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public boolean isEditable(){
		return editable;
	}
}
