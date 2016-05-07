package com.treecore.activity.layoutloader;

import com.treecore.exception.TNoSuchNameLayoutException;

import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @Title  通过资源名获取布局
 */
public interface TILayoutLoader {
	public int getLayoutID(String resIDName) throws ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException,
			NameNotFoundException, TNoSuchNameLayoutException;

}
