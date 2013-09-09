package org.webtree.System;

import org.webtree.Human.Model.HumanModel;
import org.webtree.Language.Model.LanguageModel;
import org.webtree.System.Helpers.FormatHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lucifer
 *         Date: 31.05.12
 *         Time: 11:00
 */
public class Registry {

	final protected static ThreadLocal<Registry> inst = new ThreadLocal<>();

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HumanModel human;
	protected LanguageModel language;

	public static Registry getInst() {
		if (inst.get() == null) {
			inst.set(new Registry());
		}
		return inst.get();
	}

	public String getRequestParam(String name) {
		return getRequestParam(name, null);
	}

	public String getRequestParam(String name, String defaultValue) {
		String value = getRawRequestParam(name);
		return value == null ? defaultValue : FormatHelper.clearHtml(value);
	}

	public String getRawRequestParam(String name) {
		return getRequest().getParameter(name);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void init(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void clear() {
		inst.remove();
	}

	public HumanModel getCurrentHuman() {
		return human;
	}

	public LanguageModel getLanguage() {
		return language;
	}

	public void setLanguage(LanguageModel language) {
		this.language = language;
	}

	public HumanModel getHuman() {
		return human;
	}

	public void setHuman(HumanModel human) {
		this.human = human;
	}
}