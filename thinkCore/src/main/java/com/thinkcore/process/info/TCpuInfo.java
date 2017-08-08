/*
 * Copyright (c) 2012-2013 NetEase, Inc. and other contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.thinkcore.process.info;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.thinkcore.utils.TMemoryUtils;
import com.thinkcore.utils.log.TLog;

/**
 * 某进程的cpu信息
 */
public class TCpuInfo {
	private static final String TAG = TCpuInfo.class.getSimpleName();

	private long processCpu;
	private long idleCpu;
	private long totalCpu;
	private long totalMemorySize;
	private int pid;

	//
	public TCpuInfo(int pid) {
		this.pid = pid;
		this.totalMemorySize = TMemoryUtils.getTotalMemory();
	}

	public long getProcessCpu() {
		return processCpu;
	}

	public long getTotalMemorySize() {
		return totalMemorySize;
	}

	public long getIdleCpu() {
		return idleCpu;
	}

	public long getTotalCpu() {
		return totalCpu;
	}

	// 读取cpu状态
	public void initConfig() {
		String processPid = Integer.toString(pid);
		String cpuStatPath = "/proc/" + processPid + "/stat";
		try {
			// monitor cpu stat of certain process
			RandomAccessFile processCpuInfo = new RandomAccessFile(
					cpuStatPath, "r");
			String line = "";
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.setLength(0);
			while ((line = processCpuInfo.readLine()) != null) {
				stringBuffer.append(line + "\n");
			}
			String[] tok = stringBuffer.toString().split(" ");
			processCpu = Long.parseLong(tok[13]) + Long.parseLong(tok[14]);
			processCpuInfo.close();
		} catch (FileNotFoundException e) {
			TLog.e(TAG, "FileNotFoundException: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			// monitor total and idle cpu stat of certain process
			RandomAccessFile cpuInfo = new RandomAccessFile("/proc/stat", "r");
			String[] toks = cpuInfo.readLine().split("\\s+");
			idleCpu = Long.parseLong(toks[4]);
			totalCpu = Long.parseLong(toks[1]) + Long.parseLong(toks[2])
					+ Long.parseLong(toks[3]) + Long.parseLong(toks[4])
					+ Long.parseLong(toks[6]) + Long.parseLong(toks[5])
					+ Long.parseLong(toks[7]);
			cpuInfo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取CPU名
	public String getCpuName() {
		try {
			RandomAccessFile cpuStat = new RandomAccessFile("/proc/cpuinfo",
					"r");
			String[] cpu = cpuStat.readLine().split(":"); // cpu信息的前一段是含有processor字符串，此处替换为不显示
			cpuStat.close();
			return cpu[1];
		} catch (IOException e) {
			TLog.e(TAG, "IOException: " + e.getMessage());
		}
		return "";
	}
}
