package utils;

import android.content.Context;

public class CacheUtils {

	/**
	 * ���û���
	 * 
	 * @param key
	 *            json��Url��ַ
	 * @param json
	 *            json����
	 */
	public static void setCache(String key, String json, Context context) {
		PrefUtils.setString(context, key, json);
	}

	public static String getCache(String key, Context ctx) {
		return PrefUtils.getString(ctx, key, null);
	}
}
