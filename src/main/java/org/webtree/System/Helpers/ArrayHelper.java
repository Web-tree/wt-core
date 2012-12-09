package org.webtree.System.Helpers;

/**
 * @author lucifer
 *         Date: 05.05.12
 *         Time: 8:35
 */
public class ArrayHelper {
	public static String implode(String glue, String[] array) {
		String output = "";
		if (array.length > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(array[0]);

			for (int i = 1; i < array.length; i++) {
				sb.append(glue);
				sb.append(array[i]);
			}
			output = sb.toString();
		}
		return output;
	}
}
