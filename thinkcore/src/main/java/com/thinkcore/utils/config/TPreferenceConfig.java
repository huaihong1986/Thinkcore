/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinkcore.utils.config;

import java.lang.reflect.Field;
import java.util.Map;

import com.thinkcore.utils.TReflecterUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @Android的Preference类型配置文件操作类
 */
public class TPreferenceConfig implements TIConfig{
	private Context context;
	private Editor edit = null;
	private SharedPreferences sharedPreferences;
	private String filename = TPreferenceConfig.class.getSimpleName();
	private Boolean isLoad = false;
	private static TPreferenceConfig that;

	private TPreferenceConfig() {
	}

	public void initConfig(Context context) {
		this.context = context;
		loadConfig();
	}


	/**
	 * 获得系统资源类
	 *
	 * @return
	 */
	public static TPreferenceConfig getInstance() {
		if (that == null) {
			that = new TPreferenceConfig();
		}
		return that;
	}

	private void loadConfig() {
		try {
			sharedPreferences = context.getSharedPreferences(filename,
					Context.MODE_WORLD_WRITEABLE);
			edit = sharedPreferences.edit();
			isLoad = true;
		} catch (Exception e) {
			isLoad = false;
		}
	}

	@Override
	public Boolean isLoadConfig() {
		return isLoad;
	}

	@Override
	public void close() {
	}

	@Override
	public void setString(String key, String value) {
		edit.putString(key, value);
		edit.commit();
	}

	@Override
	public void setInt(String key, int value) {
		edit.putInt(key, value);
		edit.commit();
	}

	@Override
	public void setBoolean(String key, Boolean value) {
		edit.putBoolean(key, value);
		edit.commit();
	}

	@Override
	public void setByte(String key, byte[] value) {
		setString(key, String.valueOf(value));
	}

	@Override
	public void setShort(String key, short value) {
		setString(key, String.valueOf(value));
	}

	@Override
	public void setLong(String key, long value) {
		edit.putLong(key, value);
		edit.commit();
	}

	@Override
	public void setFloat(String key, float value) {
		edit.putFloat(key, value);
		edit.commit();
	}

	@Override
	public void setDouble(String key, double value) {
		setString(key, String.valueOf(value));
	}

	@Override
	public void setString(int resID, String value) {

		setString(this.context.getString(resID), value);

	}

	@Override
	public void setInt(int resID, int value) {

		setInt(this.context.getString(resID), value);
	}

	@Override
	public void setBoolean(int resID, Boolean value) {

		setBoolean(this.context.getString(resID), value);
	}

	@Override
	public void setByte(int resID, byte[] value) {

		setByte(this.context.getString(resID), value);
	}

	@Override
	public void setShort(int resID, short value) {

		setShort(this.context.getString(resID), value);
	}

	@Override
	public void setLong(int resID, long value) {

		setLong(this.context.getString(resID), value);
	}

	@Override
	public void setFloat(int resID, float value) {

		setFloat(this.context.getString(resID), value);
	}

	@Override
	public void setDouble(int resID, double value) {

		setDouble(this.context.getString(resID), value);
	}

	@Override
	public String getString(String key, String defaultValue) {

		return sharedPreferences.getString(key, defaultValue);
	}

	@Override
	public int getInt(String key, int defaultValue) {

		return sharedPreferences.getInt(key, defaultValue);
	}

	@Override
	public boolean getBoolean(String key, Boolean defaultValue) {

		return sharedPreferences.getBoolean(key, defaultValue);
	}

	@Override
	public byte[] getByte(String key, byte[] defaultValue) {

		try {
			return getString(key, "").getBytes();
		} catch (Exception e) {

		}
		return defaultValue;
	}

	@Override
	public short getShort(String key, Short defaultValue) {

		try {
			return Short.valueOf(getString(key, ""));
		} catch (Exception e) {

		}
		return defaultValue;
	}

	@Override
	public long getLong(String key, Long defaultValue) {

		return sharedPreferences.getLong(key, defaultValue);
	}

	@Override
	public float getFloat(String key, Float defaultValue) {

		return sharedPreferences.getFloat(key, defaultValue);
	}

	@Override
	public double getDouble(String key, Double defaultValue) {

		try {
			return Double.valueOf(getString(key, ""));
		} catch (Exception e) {

		}
		return defaultValue;
	}

	@Override
	public String getString(int resID, String defaultValue) {

		return getString(this.context.getString(resID), defaultValue);
	}

	@Override
	public int getInt(int resID, int defaultValue) {

		return getInt(this.context.getString(resID), defaultValue);
	}

	@Override
	public boolean getBoolean(int resID, Boolean defaultValue) {

		return getBoolean(this.context.getString(resID), defaultValue);
	}

	@Override
	public byte[] getByte(int resID, byte[] defaultValue) {

		return getByte(this.context.getString(resID), defaultValue);
	}

	@Override
	public short getShort(int resID, Short defaultValue) {

		return getShort(this.context.getString(resID), defaultValue);
	}

	@Override
	public long getLong(int resID, Long defaultValue) {

		return getLong(this.context.getString(resID), defaultValue);
	}

	@Override
	public float getFloat(int resID, Float defaultValue) {

		return getFloat(this.context.getString(resID), defaultValue);
	}

	@Override
	public double getDouble(int resID, Double defaultValue) {

		return getDouble(this.context.getString(resID), defaultValue);
	}

	@Override
	public void setConfig(Object entity) {

		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {

			if (!TReflecterUtils.isTransient(field)) {
				if (TReflecterUtils.isBaseDateType(field)) {
					String columnName = TReflecterUtils.getFieldName(field);
					field.setAccessible(true);
					setValue(field, columnName, entity);
				}
			}
		}
	}

	private void setValue(Field field, String columnName, Object entity) {
		try {
			Class<?> clazz = field.getType();
			if (clazz.equals(String.class)) {
				setString(columnName, (String) field.get(entity));
			} else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
				setInt(columnName, (Integer) field.get(entity));
			} else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
				setFloat(columnName, (Float) field.get(entity));
			} else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
				setDouble(columnName, (Double) field.get(entity));
			} else if (clazz.equals(Short.class) || clazz.equals(Short.class)) {
				setShort(columnName, (Short) field.get(entity));
			} else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
				setLong(columnName, (Long) field.get(entity));
			} else if (clazz.equals(Boolean.class)) {
				setBoolean(columnName, (Boolean) field.get(entity));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	@Override
	public <T> T getConfig(Class<T> clazz) {

		Field[] fields = clazz.getDeclaredFields();
		T entity = null;
		try {
			entity = (T) clazz.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				if (!TReflecterUtils.isTransient(field)) {
					if (TReflecterUtils.isBaseDateType(field)) {
						String columnName = TReflecterUtils.getFieldName(field);
						field.setAccessible(true);
						getValue(field, columnName, entity);
					}
				}

			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	private <T> void getValue(Field field, String columnName, T entity) {
		try {
			Class<?> clazz = field.getType();
			if (clazz.equals(String.class)) {
				field.set(entity, getString(columnName, ""));
			} else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
				field.set(entity, getInt(columnName, 0));
			} else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
				field.set(entity, getFloat(columnName, 0f));
			} else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
				field.set(entity, getDouble(columnName, 0.0));
			} else if (clazz.equals(Short.class) || clazz.equals(Short.class)) {
				field.set(entity, getShort(columnName, (short) 0));
			} else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
				field.set(entity, getLong(columnName, 0l));
			} else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
				field.set(entity, getByte(columnName, new byte[8]));
			} else if (clazz.equals(Boolean.class)) {
				field.set(entity, getBoolean(columnName, false));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(String key) {
		edit.remove(key);
		edit.commit();
	}

	@Override
	public void remove(String... keys) {
		for (String key : keys)
			remove(key);
	}

	@Override
	public void removeAll() {
		try {
			Map<String, ?> values = sharedPreferences.getAll();
			for (String key : values.keySet()) {
				edit.remove(key);
			}
		} catch (Exception e) {
		}

		edit.commit();
	}

	@Override
	public void clear() {
		edit.clear();
		edit.commit();
	}

	@Override
	public void open() {
	}

}