package org.webtree.System.Exception;

/**
 * @author lucifer
 *         Date: 29.04.12
 *         Time: 14:44
 */
public class ItemNotFound extends WebTreeException {
	public ItemNotFound() {
		super("Мы что то не нашли.");
	}

	public ItemNotFound(String message) {
		super(message);
	}
}
