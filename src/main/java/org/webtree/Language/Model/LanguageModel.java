package org.webtree.Language.Model;

import org.webtree.Base.MVC.BaseModel;

import java.util.Locale;

/**
 * @author lucifer
 *         Date: 04.05.12
 *         Time: 20:27
 */
public class LanguageModel extends BaseModel {
	protected Locale locale;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public LanguageModel(Locale locale) {
		this.locale = locale;

	}

}
