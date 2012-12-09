package org.webtree.System.Helpers;

import org.webtree.System.Exception.WebTreeException;

import java.util.regex.Pattern;

/**
 * @author lucifer
 *         Date: 01.07.12
 *         Time: 12:50
 */
public class FormatHelper {
	public static void assertLogin(String email) throws LoginAssertFail {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9.-_@]");
		if (null == pattern.matcher(email)) {
			throw new LoginAssertFail();
		}
	}

	public static class AssertException extends WebTreeException{}
	public static class LoginAssertFail extends AssertException{}
	public static class EmailAssertFail extends AssertException{}
}
