package org.webtree.Base.BaseModule;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.webtree.Base.MVC.BaseView;
import org.webtree.Layout.Controller.LayoutController;
import org.webtree.System.Exception.MessageException;
import org.webtree.System.Helpers.ViewHelper;
import org.webtree.System.Log;
import org.webtree.System.ProjectSettings;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lucifer
 *         Date: 07.05.12
 *         Time: 8:30
 */
abstract public class BaseModuleView extends BaseView {


	protected static HashMap<String, Configuration> templateConf = new HashMap<>();

	protected ArrayList<MessageException> messages = new ArrayList<>();

	final static protected ViewHelper viewHelper = new ViewHelper();

	protected String currentMenu;

	protected FORMAT format;

	abstract protected String templateName();
	abstract protected String templateDirectory();

	public String parse() {
		String result = parseView();
		switch (getFormat()) {
			case HTML:
				LayoutController layout = new LayoutController();
				result = layout.parse(result);
				break;
			case JSON:
				result = jsonContainer(result);
				break;
		}
		return result;
	}

	public BaseModuleView() {
		setFormat(FORMAT.HTML);
	}

	public BaseModuleView(FORMAT format) {
		setFormat(format);
	}

	public FORMAT getFormat() {
		return FORMAT.HTML;
	}

	public void setFormat(FORMAT format) {
		this.format = format;
	}


	public List<MessageException> getMessages() {
		return messages;
	}

	public void addMessage(MessageException message) {
		this.messages.add(message);
	}

	public ViewHelper getViewHelper() {
		return viewHelper;
	}

	public Template getTemplate(String name, String subDir) {
		try {
			return getTemplateConf(subDir).getTemplate(name + ".ftl");
		} catch (IOException e) {
			Log.getInst().error("Get template error.", e);
			throw new Error(e);
		}
	}

	public Template getTemplate(String name) {
		String dir = "module";
		if (templateDirectory() != null) {
			dir += "/" + templateDirectory();
		}
		return getTemplate(name, dir);
	}

	public String processTemplate(Object object, Template template) {
		Writer writer = new StringWriter();
		try {
			template.process(getDefaultMap(object), writer);
		} catch (TemplateException | IOException e) {
			Log.getInst().error("Template process error", e);
			throw new Error(e);
		}
		return writer.toString();
	}

	public String processTemplate(Object object, String templateName) {
		return processTemplate(object, getTemplate(templateName));
	}

	protected Configuration getTemplateConf(String dir) {
		Configuration conf;
		conf = templateConf.get(dir);
		if (conf == null) {
			conf = new Configuration();
			conf.setDefaultEncoding("UTF-8");
			try {
				conf.setDirectoryForTemplateLoading(new File(ProjectSettings.get("projectPath") + "WEB-INF/template/html/" + dir));
			} catch (IOException e) {
				Log.getInst().error("Get template conf error.", e);
				throw new Error(e);
			}
			templateConf.put(dir, conf);
		}
		return conf;
	}

	protected String jsonContainer(String data) {
		JSONObject json = new JSONObject();
		json.put("data", data);
		if (!messages.isEmpty()) {
			JSONArray jsonMessages = new JSONArray();
			for (MessageException message : messages) {
				jsonMessages.add(message.toJson());
			}
			json.put("messages", jsonMessages);
		}
		return json.toString();
	}

	protected HashMap<String, Object> getDefaultMap(Object object) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("content", object);
		map.put("helper", getViewHelper());
		map.put("messages", getMessages());
		map.put("currentMenu", currentMenu);
		return map;
	}

	protected String parseView() {
		return processTemplate(getData(), getTemplate(templateName(), "module/" + templateDirectory()));
	}
}