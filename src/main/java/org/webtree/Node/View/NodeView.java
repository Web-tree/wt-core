package org.webtree.Node.View;

import org.webtree.Base.BaseModule.BaseModuleView;
import org.webtree.Link.Model.LinkModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lucifer
 *         Date: 16.04.12
 *         Time: 22:01
 */
abstract public class NodeView extends BaseModuleView {

	protected List<LinkModel> getMenu() {
		ArrayList<LinkModel> linkModels = new ArrayList<>();
		String basePath = "node/";
		linkModels.add(new LinkModel(basePath + "list", "Все ноды"));
		linkModels.add(new LinkModel(basePath + "list/my", "Мои ноды"));
		linkModels.add(new LinkModel(basePath + "create", "Создать нод"));
		return linkModels;
	}


	protected HashMap<String, Object> getDefaultMap(Object object) {
		HashMap<String, Object> map = super.getDefaultMap(object);
		map.put("nodeMenu", getMenu());
		return map;
	}
}
