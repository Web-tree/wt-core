package org.webtree.Cache;

import net.spy.memcached.MemcachedClient;
import org.webtree.System.Exception.WebTreeException;
import org.webtree.System.Log;
import org.webtree.System.ProjectSettings;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author lucifer
 *         Date: 12.08.12
 *         Time: 23:13
 */
public class Cache {
	protected static MemcachedClient client;

	public static void set(String key, Object value) throws CacheException {
		set(key, value, Integer.parseInt(ProjectSettings.get("memcacheDefaultExpire")));
	}

	public static void set(String key, Object value, int expire) throws CacheException {
		getConnect().set(key, expire, value);
	}

	public static Object get(String key) throws CacheException {
		return getConnect().get(key);
	}

	public static void del(String key) throws CacheException {
		getConnect().delete(key);
	}

	public static void incr(String key) throws CacheException {
		incr(key, 1);
	}

	public static void incr(String key, int value) throws CacheException {
		getConnect().incr(key, value);
	}

	public static void decr(String key) throws CacheException {
		decr(key, 1);
	}

	public static void decr(String key, int value) throws CacheException {
		getConnect().decr(key, value);
	}

	protected static MemcachedClient getConnect() throws CacheException {
		if (client == null) {
			try {
				client = new MemcachedClient(new InetSocketAddress(
				ProjectSettings.get("memcacheHost"),
				Integer.parseInt(ProjectSettings.get("memcachePort")))
				);
			} catch (IOException e) {
				Log.getInst().error("Error with get memcached", e);
				throw new CacheException();
			}
		}
		return client;
	}

	public static class CacheException extends WebTreeException {
	}
}