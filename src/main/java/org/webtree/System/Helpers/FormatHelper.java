package org.webtree.System.Helpers;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.webtree.System.Exception.WebTreeException;

import java.util.regex.Pattern;

/**
 * @author lucifer
 *         Date: 01.07.12
 *         Time: 12:50
 */
public class FormatHelper {
	private static PolicyFactory cleanPolicyFactory = new HtmlPolicyBuilder().toFactory();

	public static void assertLogin(String email) throws LoginAssertFail {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9.-_@]");
		if (null == pattern.matcher(email)) {
			throw new LoginAssertFail();
		}
	}

	public static String clearHtml(String html) {
		return cleanPolicyFactory.sanitize(html);
	}

	public static class AssertException extends WebTreeException{}
	public static class LoginAssertFail extends AssertException{}
	public static class EmailAssertFail extends AssertException{}
}
