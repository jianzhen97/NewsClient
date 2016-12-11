package utils;

import android.content.Context;

public class CacheUtils {

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            json的Url地址
	 * @param json
	 *            json数据
	 */
	public static void setCache(String key, String json, Context context) {
		PrefUtils.setString(context, key, json);
	}

	public static String getCache(String key, Context ctx) {
		return PrefUtils.getString(ctx, key, null);
	}
}
