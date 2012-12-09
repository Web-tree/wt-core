package org.webtree.Language.Controller;

import org.webtree.Base.MVC.BaseController;
import org.webtree.Language.Model.LanguageModel;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author lucifer
 *         Date: 03.05.12
 *         Time: 21:25
 */
public class LanguageController extends BaseController {
	protected List<String> availableLanguages;

	public LanguageController() {
		availableLanguages = Arrays.asList("ru");
	}

	public LanguageModel getLang(String lang) throws LangNotExists {
		if (!availableLanguages.contains(lang)) {
			throw new LangNotExists(getRequestLanguage());
		}
		return new LanguageModel(new Locale(lang));
	}

	public LanguageModel getRequestLanguage() {
		return getDefaultLanguage();
	}

	public LanguageModel getDefaultLanguage() {
		return new LanguageModel(new Locale(availableLanguages.get(0)));
	}

	public class LangNotExists extends Exception {
		public LanguageModel language;

		public LanguageModel getLanguage() {
			return language;
		}

		LangNotExists(LanguageModel language) {
			this.language = language;
		}
	}
}
